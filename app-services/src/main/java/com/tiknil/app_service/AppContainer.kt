package com.tiknil.app_service

import com.tiknil.app_service.cache.ICacheService
import dagger.Lazy
import javax.inject.Inject

class AppContainer @Inject constructor(private val cacheServiceLazy: Lazy<ICacheService>)  {

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

    fun cacheService(): ICacheService = cacheServiceLazy.get()

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