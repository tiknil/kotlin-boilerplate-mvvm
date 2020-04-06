package com.tiknil.app.core.utils

import timber.log.Timber

class TkLog {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields
    //endregion


    //region Class methods

    companion object {
        var isTestingMode = false

        fun d(message: String?, vararg args: Any?) {
            if (isTestingMode) {
                println(String.format(message!!, *args))
            } else {
                Timber.d(message, *args)
            }
        }

        fun d(t: Throwable) {
            if (isTestingMode) {
                println("EXCEPTION: " + t.message)
            } else {
                Timber.d(t)
            }
        }

        fun d(
            t: Throwable,
            message: String?,
            vararg args: Any?
        ) {
            if (isTestingMode) {
                println("EXCEPTION: " + t.message + ", " + String.format(message!!, *args))
            } else {
                Timber.d(t, message, *args)
            }
        }
    }
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
    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}