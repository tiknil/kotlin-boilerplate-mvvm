package com.tiknil.app.navigation.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.suspendCancellableCoroutine

class SuspendingStackNavigator(
        private val navigator: StackNavigator
) : SuspendingNavigator by CommonSuspendingNavigator(navigator) {

   override suspend fun clear(upToTag: String?, includeMatch: Boolean) = suspendCancellableCoroutine<Fragment?> { continuation ->
        val tag = upToTag ?: navigator.fragmentTags.firstOrNull() ?: ""
        val index = navigator.fragmentTags.indexOf(tag).let { if (includeMatch) it - 1 else it }
        val toShow = if (index < 0) null else navigator.find(navigator.fragmentTags[index] ?: "")

        navigator.clear(upToTag, includeMatch)
        toShow?.doOnLifecycleEvent(Lifecycle.Event.ON_RESUME) { continuation.resumeIfActive(toShow) }
                ?: continuation.resumeIfActive(null)
    }
}