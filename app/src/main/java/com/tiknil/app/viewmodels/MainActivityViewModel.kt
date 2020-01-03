package com.tiknil.app.viewmodels

import com.github.ajalt.timberkt.Timber
import com.tiknil.app_core.viewmodels.BaseViewModel
import com.tiknil.app_service.AppContainer

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
        Timber.d {"foo"}
        Timber.d { "${this.javaClass.name}, ${container.cacheService().foo}" }

        //container.fragmentNavigator().showFragment(0, SubFragment(), IFragmentNavigator.FragmentSlideAnimation.NO_ANIMATION )
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