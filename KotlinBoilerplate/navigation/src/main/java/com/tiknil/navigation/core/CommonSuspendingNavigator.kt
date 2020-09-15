package com.tiknil.navigation.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.tiknil.app.core.views.BaseFragment
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * A stateless class that keeps [SuspendingStackNavigator] and [SuspendingMultiStackNavigator] DRY
 */
internal class CommonSuspendingNavigator(private val navigator: INavigator) : SuspendingNavigator {
    override val containerId: Int get() = navigator.containerId

    override val current: Fragment? get() = navigator.current

    override val previous: Fragment? get() = navigator.previous

    override suspend fun find(tag: String): Fragment? = navigator.find(tag)

    override suspend fun pop() = suspendCancellableCoroutine<Fragment?> { continuation ->
        when (val previous = navigator.previous) {
            null -> continuation.resumeIfActive(null)
            else -> {
                previous.doOnLifecycleEvent(Lifecycle.Event.ON_RESUME) { continuation.resumeIfActive(previous) }
                navigator.pop()
            }
        }
    }

    override suspend fun <T : Fragment> push(fragment: T, tag: String) = suspendCancellableCoroutine<T?> { continuation ->
        when (navigator.current?.tag) {
            tag -> continuation.resumeIfActive(null)
            else -> {
                fragment.doOnLifecycleEvent(Lifecycle.Event.ON_RESUME) { continuation.resumeIfActive(fragment) }
                if(fragment is BaseFragment<*, *>) {
                    navigator.push(fragment, tag)
                }
            }
        }
    }

    override suspend fun clear(upToTag: String?, includeMatch: Boolean): Fragment? =
            throw IllegalArgumentException("Override this")
}

internal fun <T> CancellableContinuation<T>.resumeIfActive(item: T) {
    if (isActive) resume(item)
}