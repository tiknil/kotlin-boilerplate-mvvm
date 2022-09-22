package com.tiknil.app.coordinators

import com.tiknil.app.core.services.ICoordinator
import com.tiknil.app.views.fragments.MainFragment
import com.tiknil.app.navigation.core.INavigator

class MainCoordinator constructor(
    private var navigator: INavigator
) : ICoordinator {

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
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks

    override fun start() {
        val mainFragment = MainFragment()
        navigator.push(mainFragment)
    }

    override fun back() {
        TODO("Not yet implemented")
    }

    //endregion

    //region Inner classes or interfaces
    //endregion
}