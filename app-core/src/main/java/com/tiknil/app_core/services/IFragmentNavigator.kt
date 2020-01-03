package com.tiknil.app_core.services

import com.tiknil.app_core.views.BaseFragment

interface IFragmentNavigator {

    enum class FragmentSlideAnimation {
        NO_ANIMATION,
        SLIDE_LEFT_TO_RIGHT,
        SLIDE_RIGHT_TO_LEFT,
        SLIDE_TOP_TO_BOTTOM,
        SLIDE_BOTTOM_TO_TOP
    }

    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean
    )

    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    )

    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?,
        onCompletion: Runnable?
    )

    fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>)

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any,
        onCompletion: Runnable
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any,
        onCompletion: Runnable
    )

    fun stackContainsFragmentOfType(fragmentType: Class<*>): Boolean

    fun popFragment(): Boolean

    fun popFragment(animation: FragmentSlideAnimation): Boolean

    fun popFragment(animation: FragmentSlideAnimation, params: Any): Boolean

    fun actualFragment(): BaseFragment<*, *>

    fun setActualFragment(actualFragment: BaseFragment<*, *>)

    fun backstackCount(): Int

}