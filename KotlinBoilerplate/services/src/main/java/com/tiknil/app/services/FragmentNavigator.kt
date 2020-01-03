package com.tiknil.app.services

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.tiknil.app.core.services.IFragmentNavigator
import com.tiknil.app.core.utils.FragmentUtils
import com.tiknil.app.core.utils.ThreadUtils
import com.tiknil.app.core.views.BaseActivity
import com.tiknil.app.core.views.BaseFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FragmentNavigator @Inject constructor(
    private val context: Context
) : IFragmentNavigator {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private

    /**
     * Aggiunge l'animazione alla transazione di fragment passato
     *
     * @param fragmentTransaction transazione del fragment
     * @param animation           animazione da impostare
     */
    private fun addCustomTransactionAnimation(
        fragmentTransaction: FragmentTransaction?,
        animation: IFragmentNavigator.FragmentSlideAnimation?
    ) {
        if (fragmentTransaction != null && animation != null) {
            when (animation) {
                IFragmentNavigator.FragmentSlideAnimation.NO_ANIMATION -> {
                }
                IFragmentNavigator.FragmentSlideAnimation.SLIDE_LEFT_TO_RIGHT -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_enter_from_left_to_right,
                    R.anim.slide_exit_from_left_to_right
                )
                IFragmentNavigator.FragmentSlideAnimation.SLIDE_RIGHT_TO_LEFT -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_enter_from_right_to_left, R.anim.slide_exit_from_right_to_left,
                    R.anim.slide_enter_from_left_to_right, R.anim.slide_exit_from_left_to_right
                )
                IFragmentNavigator.FragmentSlideAnimation.SLIDE_TOP_TO_BOTTOM -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_enter_from_top_to_bottom,
                    R.anim.slide_exit_from_top_to_bottom
                )
                IFragmentNavigator.FragmentSlideAnimation.SLIDE_BOTTOM_TO_TOP -> fragmentTransaction.setCustomAnimations(
                    R.anim.slide_enter_from_bottom_to_top, R.anim.slide_exit_from_bottom_to_top,
                    R.anim.slide_enter_from_top_to_bottom, R.anim.slide_exit_from_top_to_bottom
                )
            }
        }
    }

    /**
     * Riprova la funzione di showfragment dopo un attimo
     * @param layoutId
     * @param fragment
     * @param animation
     * @param replace
     * @param params
     * @param onCompletion
     */
    private fun tryAgainLater(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean,
        params: Any?,
        onCompletion: Runnable?
    ) {
        // Se l'activity non è pronta riprova tra 100 milli
        val op = Observable.timer(100, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showFragment(
                    layoutId,
                    fragment,
                    animation,
                    replace,
                    params,
                    onCompletion
                )
            }
    }

    //endregion


    //region Override methods and callbacks

    override var currentFragment: BaseFragment<*, *>? = null

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation
    ) {
        showFragment(layoutId, fragment, animation, false)
    }

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     */
    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean
    ) {
        showFragment(layoutId, fragment, animation, replace, null)
    }

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     */
    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    ) {
        showFragment(layoutId, fragment, animation, replace, params, null)
    }

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean,
        params: Any?,
        onCompletion: Runnable?
    ) {
        val currentActivity: BaseActivity<*, *>? = currentFragment?.activity as BaseActivity<*, *>
        if (currentActivity != null) {
            val fm = currentActivity.supportFragmentManager

            if (!currentActivity.isFinishing && !currentActivity.isDestroyed && (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || !currentActivity.isActivityTransitionRunning)
            ) {
                val fragmentTransaction = fm.beginTransaction()

                addCustomTransactionAnimation(fragmentTransaction, animation)

                // Esecuzione della visualizzazione del fragment all'interno dello UIThread
                ThreadUtils.runOnUiThread(currentFragment?.viewLifecycleOwner!!.lifecycleScope) {
                    try {
                        if (replace and (fm.backStackEntryCount == 0)) {
                            fragmentTransaction
                                .replace(layoutId, fragment, fragment.javaClass.simpleName)
                        } else {
                            if (replace) {
                                fm.popBackStack()
                            }
                            fragmentTransaction
                                .add(layoutId, fragment)
                                .addToBackStack(fragment.javaClass.simpleName)
                        }
                        fragmentTransaction
                            .commit()
                    } catch (exception: Exception) {
                        tryAgainLater(layoutId, fragment, animation, replace, params, onCompletion)
                    }
                }

                currentFragment?.onViewDisappear()
                currentFragment = fragment
                currentFragment!!.params = params
                currentFragment!!.onViewAppear()

                onCompletion?.run()
            } else {
                tryAgainLater(layoutId, fragment, animation, replace, params, onCompletion)
            }
        } else {
            tryAgainLater(layoutId, fragment, animation, replace, params, onCompletion)
        }
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     */
    override fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>) {
        resetStackAndShowFragment(layoutId, fragment, IFragmentNavigator.FragmentSlideAnimation.NO_ANIMATION)
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation
    ) {
        resetStackAndShowFragment(layoutId, fragment, animation, null, null)
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     */
    override fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>, params: Any?) {
        resetStackAndShowFragment(layoutId, fragment, params, null)
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     */
    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any?
    ) {
        resetStackAndShowFragment(layoutId, fragment, animation, params, null)
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any?,
        onCompletion: Runnable?
    ) {
        resetStackAndShowFragment(layoutId, fragment, IFragmentNavigator.FragmentSlideAnimation.NO_ANIMATION, params, onCompletion)
    }

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any?,
        onCompletion: Runnable?
    ) {
        val currentActivity: BaseActivity<*, *>? = currentFragment?.activity as BaseActivity<*, *>
        if (currentActivity != null) {
            val fm = currentActivity.supportFragmentManager

            if (!currentActivity.isFinishing && !currentActivity.isDestroyed && (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || !currentActivity.isActivityTransitionRunning)
            ) {
                val fragmentTransaction = fm.beginTransaction()

                if (currentFragment != null) {
                    currentFragment!!.onViewDisappear()
                }
                for (i in fm.backStackEntryCount - 1 downTo 0) { // Poichè si esegue un reset dello stack, viene rimossa l'animazione
                    FragmentUtils.sDisableFragmentAnimations = true
                    fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    FragmentUtils.sDisableFragmentAnimations = false
                }
                addCustomTransactionAnimation(fragmentTransaction, animation)

                fragmentTransaction.commit()

                val op = Observable.timer(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        showFragment(
                            layoutId,
                            fragment,
                            IFragmentNavigator.FragmentSlideAnimation.NO_ANIMATION,
                            false,
                            params,
                            onCompletion
                        )
                    }
            }
        }
    }

    /**
     * Ritorna true se lo stack contiene il fragment del tipo passato, false altrimenti
     *
     * @param fragmentType          il tipo di fragment
     */
    override fun stackContainsFragmentOfType(fragmentType: Class<*>): Boolean {
        val currentActivity: BaseActivity<*, *>? = currentFragment?.activity as BaseActivity<*, *>
        if (currentActivity != null) {
            val fm = currentActivity.supportFragmentManager
            for (i in 0 until fm.backStackEntryCount) {
                if (fm.getBackStackEntryAt(i).name != null &&
                    fm.getBackStackEntryAt(i).name.equals(fragmentType.simpleName, true)
                ) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Esegue il pop del primo fragment presente nello stack
     */
    override fun popFragment(): Boolean {
        return popFragment(IFragmentNavigator.FragmentSlideAnimation.SLIDE_LEFT_TO_RIGHT)
    }

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param animation         l'animazione con cui eseguire il fragment
     */
    override fun popFragment(animation: IFragmentNavigator.FragmentSlideAnimation): Boolean {
        return popFragment(animation, null)
    }

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param animation         l'animazione con cui eseguire il fragment
     * @param params            i parametri da passare al fragment
     */
    override fun popFragment(
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any?
    ): Boolean {
        var result = false
        val currentActivity: BaseActivity<*, *>? = currentFragment?.activity as BaseActivity<*, *>
        if (currentActivity != null) {
            val fm = currentActivity.supportFragmentManager

            if (!currentActivity.isFinishing && !currentActivity.isDestroyed && (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || !currentActivity.isActivityTransitionRunning)
            ) {
                result = true
                val fragmentTransaction = fm.beginTransaction()
                addCustomTransactionAnimation(fragmentTransaction, animation)

                if (currentFragment != null) {
                    currentFragment!!.onViewDisappear()
                }

                if (fm.fragments.size > 1) {
                    val fragment: Fragment = fm.fragments[fm.fragments.size - 2]
                    if (fragment is BaseFragment<*, *>) {
                        currentFragment = fragment
                        currentFragment!!.params = params
                        currentFragment!!.onViewAppear()
                    }
                } else {
                    currentFragment = null
                }

                // Esecuzione della visualizzazione del fragment all'interno dello UIThread
                ThreadUtils.runOnUiThread(currentFragment?.viewLifecycleOwner!!.lifecycleScope) {
                    fm.popBackStack()
                    fragmentTransaction.commit()
                }
            }
        }
        return result
    }

    /**
     * Ritorna il numero di fragment nello stack
     */
    override fun backstackCount(): Int {
        var result = 0
        val currentActivity: BaseActivity<*, *>? = currentFragment?.activity as BaseActivity<*, *>
        if (currentActivity != null) {
            val fm = currentActivity.supportFragmentManager
            result = fm.backStackEntryCount
        }
        return result
    }


    //endregion

    //region Inner classes or interfaces
    //endregion
}