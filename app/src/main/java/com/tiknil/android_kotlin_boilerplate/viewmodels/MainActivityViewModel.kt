package com.tiknil.android_kotlin_boilerplate.viewmodels

import android.util.Log
import com.tiknil.android_kotlin_boilerplate.services.AppContainer

class MainActivityViewModel(container: AppContainer): BaseViewModel(container) {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields
    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
    //endregion


    //region Custom accessors
    //endregion


    //region Public

    fun foo() {
        Log.d(this.javaClass.name, "foo")
        Log.d(this.javaClass.name, container.cacheService().foo)
    }

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