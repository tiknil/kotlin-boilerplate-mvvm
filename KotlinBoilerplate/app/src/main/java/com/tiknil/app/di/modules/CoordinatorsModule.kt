package com.tiknil.app.di.modules

import com.tiknil.app.coordinators.AppCoordinator
import com.tiknil.app.core.services.ICoordinator

class CoordinatorsModule {

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

    /**
     * AppContainer
     */
    fun provideAppCoordinator(): ICoordinator = AppCoordinator()

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