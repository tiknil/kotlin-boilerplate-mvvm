package com.tiknil.app.di

import android.content.Context
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.core.services.*
import com.tiknil.app.services.*
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Modulo Dagger2 dell'app che definisce come gestire e creare le istanze che vengono injectate all'interno del progetto
 */

@Module
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

    @Provides
    fun provideAppContainer(
        context: Lazy<Context>,
        cacheService: Lazy<ICacheService>,
        activityNavigator: Lazy<IActivityNavigator>,
        fragmentNavigator: Lazy<IFragmentNavigator>
    ): IAppContainer = AppContainer(
        context,
        cacheService,
        activityNavigator,
        fragmentNavigator
    )

    /**
     * Context
     */
    @Provides
    @Singleton
    fun provideContext(): Context = KotlinBoilerplateApp.app.applicationContext

    /**
     * ICacheService
     */
    @Provides
    @Singleton
    fun provideCacheService(): ICacheService =
        CacheService()

    /**
     * IActivityNavigator
     */
    @Provides
    fun provideActivityNavigator(): IActivityNavigator {
        return ActivityNavigator()
    }

    /**
     * IFragmentNavigator
     */
    @Provides
    fun provideFragmentNavigator(): IFragmentNavigator {
        return FragmentNavigator()
    }

    /**
     * IDataService
     */
    @Provides
    @Singleton
    fun provideDataService(cacheService: ICacheService): IDataService =
        DataService(cacheService)

    /**
     * IRestService
     */
    @Provides
    @Singleton
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