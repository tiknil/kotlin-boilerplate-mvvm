package com.tiknil.app.viewmodels.activities

import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.services.AppContainer
import com.tiknil.app.viewmodels.BaseViewModel

class MainActivityVM(container: IAppContainer): BaseViewModel(container) {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreated() {
        super.onCreated()
        (context() as KotlinBoilerplateApp).appCoordinator.start()
    }

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