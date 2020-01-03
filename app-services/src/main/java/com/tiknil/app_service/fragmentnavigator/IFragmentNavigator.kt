package com.tiknil.app_service.fragmentnavigator

import androidx.fragment.app.Fragment

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
        fragment: Fragment,
        animation: FragmentSlideAnimation
    )

    fun showFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation,
        replace: Boolean
    )

    fun showFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    )

    fun showFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?,
        onCompletion: Runnable?
    )

    fun resetStackAndShowFragment(layoutId: Int, fragment: Fragment)

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: Fragment,
        params: Any
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation,
        params: Any
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: Fragment,
        params: Any,
        onCompletion: Runnable
    )

    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: Fragment,
        animation: FragmentSlideAnimation,
        params: Any,
        onCompletion: Runnable
    )

    fun stackContainsFragmentOfType(fragmentType: Class<*>): Boolean

    fun popFragment(): Boolean

    fun popFragment(animation: FragmentSlideAnimation): Boolean

    fun popFragment(animation: FragmentSlideAnimation, params: Any): Boolean

    fun actualFragment(): Fragment

    fun setActualFragment(Fragment: Fragment)

    fun backstackCount(): Int

}