package com.tiknil.app.viewmodels.fragment

import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.viewmodels.BaseVM
import timber.log.Timber

class MainFragmentVM: BaseVM() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields
    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    init {
        logServices()
        setDemoModules()
        logServices()
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private

    private fun logServices() {
        Timber.d("===>CONTEXT: ${context()}")
        Timber.d("===>DATA_SERVICE: ${dataService()}")
        Timber.d("===>REST_SERVICE: ${restService()}")
    }
    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}