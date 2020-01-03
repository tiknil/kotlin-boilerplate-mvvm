package com.tiknil.app_service.fragmentnavigator

import android.content.Context
import android.os.Build
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.tiknil.app_core.BaseActivity
import com.tiknil.app_core.BaseFragment
import com.tiknil.app_core.BaseViewModel
import com.tiknil.app_core.interfaces.IFragmentNavigator
import com.tiknil.app_core.utils.ThreadUtils
import com.tiknil.app_service.R
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

    private var currentFragment: BaseFragment<*, *>? = null

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

    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation
    ) {
        showFragment(layoutId, fragment, animation, false)
    }

    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean
    ) {
        showFragment(layoutId, fragment, animation, replace, null)
    }

    override fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    ) {
        showFragment(layoutId, fragment, animation, replace, params, null)
    }

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

    override fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>, params: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any,
        onCompletion: Runnable
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any,
        onCompletion: Runnable
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stackContainsFragmentOfType(fragmentType: Class<*>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun popFragment(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun popFragment(animation: IFragmentNavigator.FragmentSlideAnimation): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun popFragment(
        animation: IFragmentNavigator.FragmentSlideAnimation,
        params: Any
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun actualFragment(): BaseFragment<*, *> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setActualFragment(actualFragment: BaseFragment<*, *>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backstackCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //endregion

    //region Inner classes or interfaces
    //endregion
}