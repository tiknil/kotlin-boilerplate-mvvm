package com.tiknil.app.core.utils
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ThreadUtils {

    fun runOnCoroutineScope(scope: CoroutineScope, runnable: () -> Any) {
        scope.launch(Dispatchers.Main.immediate) {
            runnable()
        }
    }

    fun runOnUiThread(runnable: () -> Unit) {
        Handler(Looper.getMainLooper()).post(runnable)
    }

}