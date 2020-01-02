package com.tiknil.app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.tiknil.app_service.AppContainer

class MainActivityViewModel(container: AppContainer): BaseViewModel(container) {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    // Create a LiveData with a String
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle


    init {
        currentName.value = "banana"
    }
    //endregion


    //region Custom accessors
    //endregion


    //region Public

    fun foo() {
        Timber.d {"foo"}
        Timber.d { "${this.javaClass.name}, ${container.cacheService().foo}" }
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