package com.tiknil.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * A like for like API of a [INavigator] that returns suspending equivalents of the original
 * functions that complete when the navigation action has finished. This typically means
 * the [Fragment] navigated to is in the [Lifecycle.State.RESUMED] state
 */
interface SuspendingNavigator {
    /**
     * @see INavigator.containerId
     */
    @get:IdRes
    val containerId: Int

    /**
     * @see INavigator.current
     */
    val current: Fragment?

    /**
     * @see INavigator.previous
     */
    val previous: Fragment?

    /**
     * @see INavigator.find
     */
    suspend fun find(tag: String): Fragment?

    /**
     * @see INavigator.pop
     */
    suspend fun pop(): Fragment?

    /**
     * @see INavigator.clear
     */
    suspend fun clear(upToTag: String? = null, includeMatch: Boolean = false): Fragment?

    /**
     * @see INavigator.push
     */
    suspend fun <T : Fragment> push(fragment: T, tag: String): T?

    /**
     * @see INavigator.push
     */
    suspend fun <T : Fragment> push(fragment: T): T? = push(fragment, fragment.navigatorTag)
}