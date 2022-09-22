package com.tiknil.app.navigation.core

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Convenience method for [Fragment] delegation to a [FragmentActivity] when implementing
 * [INavigator.Controller]
 */
inline fun <reified T : INavigator> Fragment.activityNavigatorController() = object : ReadOnlyProperty<Fragment, T> {
    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        (activity as? INavigator.Controller)?.INavigator as? T
            ?: throw IllegalStateException("The hosting Activity is not a Navigator Controller")
}

/**
 * Provides a window into the Navigator. This type is returned when the [INavigator] is not
 * guaranteed to be attached, and it is therefore unsafe to perform any mutating calls with it.
 */
interface ReadOnlyNavigator {
    /**
     * The id of the container this [INavigator] shows [Fragment]s in
     */
    @get:IdRes
    val containerId: Int

    /**
     * The current visible [Fragment] the User can interact with
     */
    val current: Fragment?

    /**
     * The [Fragment] that will become the [current] following a [INavigator.pop]
     */
    val previous: Fragment?

    /**
     * Finds a [Fragment] that has previously been shown with this [INavigator]
     */
    fun find(tag: String): Fragment?
}

/**
 * An interface for managing and showing [Fragment] instances
 */
interface INavigator : ReadOnlyNavigator {

    /**
     * Pops the current fragment off the stack, up until the last fragment.
     *
     * @return true if a fragment was popped, false if the stack is down to the last fragment.
     */
    fun pop(): Boolean

    /**
     * Pops the stack up to the [upToTag] value. If null is passed as the value,
     * the stack will be popped to the root [Fragment].
     * By default it doesn't pop the Fragment matching the [upToTag]; to do so, pass true for the
     * [includeMatch] parameter.
     */
    fun clear(upToTag: String? = null, includeMatch: Boolean = false)

    /**
     *
     */
    fun push(fragment: Fragment, params: Any?, tag: String, replace: Boolean): Boolean

    /**
     *
     */
    fun push(fragment: Fragment, params: Any?, tag: String) = push(fragment, params, tag, true)
    /**
     *
     */
    fun push(fragment: Fragment, params: Any?, replace: Boolean) = push(fragment, params, fragment.navigatorTag, replace)

    /**
     *
     */
    fun push(fragment: Fragment, replace: Boolean) = push(fragment, null, fragment.navigatorTag, replace)

    /**
     *
     */
    fun push(fragment: Fragment, params: Any?) = push(fragment, params, fragment.navigatorTag, true)

    /**
     * Attempts to show the fragment provided, retrieving it from the back stack
     * if an identical instance of it already exists in the [FragmentManager] under the specified
     * tag.
     */
    fun push(fragment: Fragment, tag: String) = push(fragment, null, tag, true)

    /**
     * Attempts to show the fragment provided, retrieving it from the back stack
     * if an identical instance of it already exists in the [FragmentManager] under the specified
     * tag.
     *
     * This is a convenience method for showing a [Fragment] without having to explicitly provide a
     * tag
     *
     * @see push
     */
    fun push(fragment: Fragment) = push(fragment, null, fragment.navigatorTag, true)

    /**
     * Verifica se il [Fragment] corrente è l'ultimo dello stack
     */
    fun isAtRoot(): Boolean

    /**
     * An interface to provide unique tags for [Fragment]. Fragment implementers typically delegate
     * this to a hash string of their arguments.
     *
     * It's convenient to let  Fragments implement this interface, along with [TransactionModifier].
     */
    interface TagProvider {
        val stableTag: String
    }

    /**
     * An interface for augmenting the [FragmentTransaction] that will show
     * the incoming Fragment. Implementers typically configure mappings for
     * shared element transitions, or other kinds of animations.
     *
     * It's convenient to let Fragments implement this interface, along with [TagProvider].
     */
    interface TransactionModifier {
        fun augmentTransaction(transaction: FragmentTransaction, incomingFragment: Fragment)
    }

    /**
     * Interface for a class that hosts a [INavigator]
     */
    interface Controller {
        val INavigator: INavigator
    }
}