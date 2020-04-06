package com.tiknil.app.coordinators

import com.tiknil.app.core.services.IActivityReference
import com.tiknil.app.core.services.ICoordinator
import com.tiknil.app.viewmodels.BaseFlowDelegate

// Delegate che definisce dei metodi comuni a tutti i CoordinatorDelegate
interface BaseCoordinatorDelegate {
    fun setCurrentCoordinator(coordinator: AbstractBaseCoordinator)
}

/**
 * Classe base per i coordinator
 */
abstract class AbstractBaseCoordinator : ICoordinator, BaseFlowDelegate {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    lateinit var activityReferenceDelegate: IActivityReference

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
    //endregion


    //region Custom accessors
    //endregion


    //region Public

    override fun start() {}

    override fun back() {}

    //endregion

    //region Protected, without modifier

    protected var canBack = false

    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks

    //endregion

    //region Inner classes or interfaces
    //endregion







}