package com.tiknil.app.services

import android.content.Context
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.IDataService
import com.tiknil.app.core.services.ServicesType

class AppContainer constructor(
    private val contextLazy: Lazy<Context>,
    private val dataServiceLazy: Lazy<IDataService>
) : IAppContainer {

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

    private fun getServicesType() : ServicesType {
        return if (isDemo) ServicesType.DEMO else ServicesType.NORMAL
    }

    //endregion


    //region Override methods and callbacks

    override var isDemo: Boolean = false
        set(value) {
            field = value
        }

    override fun context(): Context = contextLazy.get()

    override fun dataService(): IDataService = dataServiceLazy.get()

    //endregion

    //region Inner classes or interfaces
    //endregion
}