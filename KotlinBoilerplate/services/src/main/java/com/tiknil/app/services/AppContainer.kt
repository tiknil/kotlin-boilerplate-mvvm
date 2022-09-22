package com.tiknil.app.services

import android.content.Context
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.IDataService
import com.tiknil.app.core.services.IRestService

class AppContainer constructor(
    private val context: Context,
    private val dataService: IDataService,
    private val restService: IRestService
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

    //endregion


    //region Override methods and callbacksx

    override fun context(): Context = context

    override fun dataService(): IDataService = dataService

    override fun restService(): IRestService = restService

    //endregion

    //region Inner classes or interfaces
    //endregion
}