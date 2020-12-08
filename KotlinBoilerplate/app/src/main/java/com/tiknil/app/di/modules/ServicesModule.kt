package com.tiknil.app.di.modules

import android.content.Context
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.ICacheService
import com.tiknil.app.core.services.IDataService
import com.tiknil.app.core.services.IRestService
import com.tiknil.app.services.AppContainer
import com.tiknil.app.services.CacheService
import com.tiknil.app.services.DataService
import com.tiknil.app.services.RestService
import dagger.Lazy

class ServicesModule {

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

    fun provideAppContainer(
        contextLazy: Lazy<Context>,
        dataServiceLazy: Lazy<IDataService>
    ): IAppContainer = AppContainer(
        contextLazy,
        dataServiceLazy
    )

    /**
     * Context
     */
    fun provideContext(): Context = KotlinBoilerplateApp.app.applicationContext

    /**
     * ICacheService
     */
    fun provideCacheService(): ICacheService =
        CacheService()

    /**
     * IDataService
     */
    fun provideDataService(cacheService: ICacheService): IDataService =
        DataService(cacheService)

    /**
     * IRestService
     */
    fun provideRestService(): IRestService = RestService()

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