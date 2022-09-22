package com.tiknil.app.navigation.extensions

import androidx.fragment.app.FragmentTransaction
import com.tiknil.app.R

const val BACKGROUND_TINT_DURATION = 1200L

fun FragmentTransaction.crossFade() = setCustomAnimations(
    android.R.anim.fade_in,
    android.R.anim.fade_out,
    android.R.anim.fade_in,
    android.R.anim.fade_out
)

fun FragmentTransaction.slideLeftToRight() = setCustomAnimations(
    R.anim.slide_enter_from_left_to_right,
    R.anim.slide_exit_from_left_to_right
)

fun FragmentTransaction.slideRightToLeft() = setCustomAnimations(
    R.anim.slide_enter_from_right_to_left, R.anim.slide_exit_from_right_to_left,
    R.anim.slide_enter_from_left_to_right, R.anim.slide_exit_from_left_to_right
)

fun FragmentTransaction.slideTopToBottom() = setCustomAnimations(
    R.anim.slide_enter_from_top_to_bottom,
    R.anim.slide_exit_from_top_to_bottom
)

fun FragmentTransaction.slideBottomToTop() = setCustomAnimations(
    R.anim.slide_enter_from_bottom_to_top, R.anim.slide_exit_from_bottom_to_top,
    R.anim.slide_enter_from_top_to_bottom, R.anim.slide_exit_from_top_to_bottom
)

