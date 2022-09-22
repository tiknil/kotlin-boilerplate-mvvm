package com.tiknil.app.navigation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import com.tiknil.app.navigation.savestate.LifecycleSavedStateContainer
import com.tiknil.app.navigation.savestate.savedStateFor
import com.tiknil.app.views.fragments.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

const val MULTI_STACK_NAVIGATOR = "com.tiknil.navigation.MultiStackNavigator"

fun Fragment.childMultiStackNavigationController(
        stackCount: Int,
        @IdRes containerId: Int,
        rootFunction: (Int) -> BaseFragment<*, *>
): Lazy<MultiStackNavigator> = lazy {
    MultiStackNavigator(
            stackCount,
            savedStateFor(this@childMultiStackNavigationController, "$MULTI_STACK_NAVIGATOR-${Random().nextInt()}"),
            childFragmentManager,
            containerId, rootFunction
    )
}

fun FragmentActivity.multiStackNavigationController(
        stackCount: Int,
        @IdRes containerId: Int,
        rootFunction: (Int) -> BaseFragment<*, *>
): Lazy<MultiStackNavigator> = lazy {
    MultiStackNavigator(
            stackCount,
            savedStateFor(this@multiStackNavigationController, "$MULTI_STACK_NAVIGATOR-${Random().nextInt()}"),
            supportFragmentManager,
            containerId,
            rootFunction
    )
}

/**
 * Manages navigation for independent stacks of [Fragment]s, where each stack is managed by a
 * [StackNavigator].
 */
class MultiStackNavigator(
    stackCount: Int,
    stateContainer: LifecycleSavedStateContainer,
    private var fragmentManager: FragmentManager,
    @IdRes override val containerId: Int,
    private val rootFunction: (Int) -> BaseFragment<*, *>) : INavigator {

    /**
     * A callback that will be invoked when a stack is selected, either by the user selecting it,
     * or from popping another stack off.
     */
    var stackSelectedListener: ((Int) -> Unit)? = null

    /**
     * Allows for the customization or augmentation of the [FragmentTransaction] that switches
     * from one active stack to another
     */
    var stackTransactionModifier: (FragmentTransaction.(Int) -> Unit)? = null

    /**
     * Allows for the customization or augmentation of the [FragmentTransaction] that will show
     * a [Fragment] inside the stack in focus
     */
    var transactionModifier: (FragmentTransaction.(Fragment) -> Unit)? = null
        set(value) {
            field = value
            stackFragments
                    .filter { it.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) }
                    .forEach { it.navigator.transactionModifier = value }
        }

    private val indices = 0 until stackCount
    internal val stackVisitor = MultiStackVisitor(stateContainer)

    /**
     * Mutable only because [clearAll]  replaces [StackFragment] instances. The alternative would
     * be a computed property delegated to [FragmentManager.addedStackFragments], which has too
     * many iterations in it's corresponding [FragmentManager.findFragmentByTag] to be justifiable.
     */
    internal var stackFragments: List<StackFragment>

    private val activeFragment: StackFragment
        get() = stackFragments.run { firstOrNull(Fragment::isAttached) ?: first() }

    val activeIndex
        get() = activeFragment.index

    val activeNavigator
        get() = activeFragment.navigator

    override val current: Fragment?
        get() = activeNavigator.current

    init {
        fragmentManager.registerFragmentLifecycleCallbacks(StackLifecycleCallback(), false)

        if (stateContainer.isFreshState) fragmentManager.commitNow { addStackFragments() }

        stackFragments = fragmentManager.addedStackFragments(indices)
    }

    /**
     * Show the stack at the specified index
     */
    fun show(index: Int) = showInternal(index, true)

    /**
     * Returns the [INavigator] at the specified index.
     *
     * This [INavigator] may not be attached to the [FragmentActivity], hence it only permits read
     * actions. Write and modification actions should be called on the [activeNavigator] instead.
     */
    fun navigatorAt(index: Int): ReadOnlyNavigator? = stackFragments.getOrNull(index)?.navigator

    /**
     * Removes all [Fragment]s from this [MultiStackNavigator] effectively resetting it.
     * After this call the [rootFunction] will be re-invoked for the first stack, allowing for the
     * replacement for the first [Fragment] shown; this is very useful for auth and de-auth flows.
     */
    fun clearAll() = fragmentManager.commitNow {
        stackVisitor.leaveAll()
        stackFragments.forEach { remove(it) }
        addStackFragments()
        runOnCommit { stackFragments = fragmentManager.addedStackFragments(indices) }
    }

    override val previous: Fragment?
        get() = when (val peeked = activeNavigator.previous) {
            is Fragment -> peeked
            else -> stackVisitor.previousHost()?.let {
                stackFragments.elementAtOrNull(it)?.navigator?.current
            }
        }

    /**
     * Pops the current fragment off the stack in focus. If The current
     * Fragment is the only Fragment on it's stack, the stack that was active before the current
     * stack is switched to.
     *
     * @see [StackNavigator.pop]
     */
    override fun pop(): Boolean = when {
        activeFragment.navigator.pop() ->
            true
        stackVisitor.leave(activeFragment.index) ->
            showInternal(stackVisitor.currentHost(), false).let { true }
        else ->
            false
    }

    override fun clear(upToTag: String?, includeMatch: Boolean) = activeNavigator.clear(upToTag, includeMatch)

    override fun isAtRoot(): Boolean = activeNavigator.isAtRoot()

    override fun push(fragment: Fragment, params: Any?, tag: String, replace: Boolean): Boolean = activeNavigator.push(fragment, params, tag, replace)

    override fun push(fragment: Fragment, params: Any?): Boolean = activeNavigator.push(fragment, params)

    override fun push(fragment: Fragment, tag: String): Boolean = activeNavigator.push(fragment, tag)

    override fun push(fragment: Fragment, params: Any?, replace: Boolean) = activeNavigator.push(fragment, params, replace)

    override fun push(fragment: Fragment, replace: Boolean) = activeNavigator.push(fragment, replace)

    override fun find(tag: String): Fragment? = activeNavigator.find(tag)
            ?: stackFragments
                    .asSequence()
                    .map(StackFragment::navigator)
                    .filter { it != activeNavigator }
                    .map { it.find(tag) }
                    .firstOrNull()

    fun performConsecutively(scope: CoroutineScope, block: suspend SuspendingMultiStackNavigator.() -> Unit) {
        scope.launch {
            block(SuspendingMultiStackNavigator(this@MultiStackNavigator))
        }
    }

    private fun showInternal(index: Int, addTap: Boolean) {
        if(!fragmentManager.isDestroyed) {
            fragmentManager.commit {
                val toShow = stackFragments[index]
                if (addTap) stackVisitor.visit(toShow.index)

                stackTransactionModifier?.invoke(this, index)

                transactions@ for (fragment in stackFragments) when {
                    fragment.index == index && !fragment.isDetached -> continue@transactions
                    fragment.index == index && fragment.isDetached -> attach(fragment)
                    else -> if (!fragment.isDetached) detach(fragment)
                }
            }
        }
    }

    /*private fun showInternal(index: Int, addTap: Boolean) = fragmentManager.commit {
        val toShow = stackFragments[index]
        if (addTap) stackVisitor.visit(toShow.index)

        stackTransactionModifier?.invoke(this, index)

        transactions@ for (fragment in stackFragments) when {
            fragment.index == index && !fragment.isDetached -> continue@transactions
            fragment.index == index && fragment.isDetached -> attach(fragment)
            else -> if (!fragment.isDetached) detach(fragment)
        }
    }*/

    private fun FragmentTransaction.addStackFragments() {
        indices.forEach { index ->
            stackTransactionModifier?.invoke(this, index)
            add(containerId, StackFragment.newInstance(index), index.toString())
        }
    }

    private fun StackFragment.showRoot() = rootFunction(index).let(navigator::push)

    private inner class StackLifecycleCallback : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentCreated(fm: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) = fragment.run {
            if (id != this@MultiStackNavigator.containerId) return
            check(this is StackFragment) { "Only Stack Fragments may be added to a container View managed by a MultiStackNavigator" }

            if (index != stackVisitor.currentHost() && isAttached) fm.commit { detach(this@run) }
        }

        override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) = fragment.run {
            if (id != this@MultiStackNavigator.containerId) return
            check(this is StackFragment) { "Only Stack Fragments may be added to a container View managed by a MultiStackNavigator" }

            navigator.transactionModifier = this@MultiStackNavigator.transactionModifier
            if (index == stackVisitor.currentHost()) {
                stackSelectedListener?.invoke(index)
                if (hasNoRoot) showRoot()
            }
        }
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class StackFragment : Fragment() {

    internal var index: Int by args()
    private var containerId: Int by args()

    internal val hasNoRoot get() = navigator.current == null
    internal val navigator by lazy { StackNavigator(childFragmentManager, containerId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            FragmentContainerView(inflater.context).apply { id = containerId }

    companion object {
        internal fun newInstance(index: Int) = StackFragment().apply { this.index = index; containerId = View.generateViewId() }
    }
}

internal class MultiStackVisitor(private val container: LifecycleSavedStateContainer) {

    private val delegate = (container.savedState.getIntArray(NAV_STACK_ORDER)
            ?: intArrayOf(0)).toMutableList()

    fun visit(value: Int) = delegate.run {
        remove(value) // No duplicates
        add(value)
        saveState()
    }

    fun leave(value: Int): Boolean = delegate.run {
        val willLeave = size > 1
        if (willLeave) {
            remove(value)
            saveState()
        }
        return willLeave
    }

    fun leaveAll(): Unit = delegate.run {
        clear()
        add(0)
        saveState()
    }

    fun currentHost(): Int = delegate.last()

    fun hosts(): IntArray = delegate.toIntArray()

    fun previousHost() = delegate.run { elementAtOrNull(lastIndex - 1) }

    private fun saveState() = container.savedState.putIntArray(NAV_STACK_ORDER, hosts())
}

const val NAV_STACK_ORDER = "navState"

private val Fragment.isAttached get() = !isDetached

private fun FragmentManager.addedStackFragments(indices: IntRange) = indices
        .map(Int::toString)
        .map(::findFragmentByTag)
        .filterIsInstance(StackFragment::class.java)
