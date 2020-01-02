package com.tiknil.app.utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ThreadUtils {

    fun runOnUiThread(scope: CoroutineScope, runnable: () -> Any) {
        scope.launch(Dispatchers.Main.immediate) {
            runnable()
        }
    }

}