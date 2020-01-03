package com.tiknil.app.di

import android.content.Context
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app_service.AppContainer
import com.tiknil.app_service.ActivityNavigator
import com.tiknil.app_core.services.IActivityNavigator
import com.tiknil.app_core.services.IAppContainer
import com.tiknil.app_service.CacheService
import com.tiknil.app_core.services.ICacheService
import com.tiknil.app_service.FragmentNavigator
import com.tiknil.app_core.services.IFragmentNavigator
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Modulo Dagger2 dell'app che definisce come gestire e creare le istanze che vengono injectate all'interno del progetto
 */

@Module
class AppModule {

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

    @Provides
    @Singleton
    fun provideContext(): Context = KotlinBoilerplateApp.app.applicationContext

    @Provides
    @Singleton
    fun provideCacheService(): ICacheService =
        CacheService()

    @Provides
    fun provideActivityNavigator(): IActivityNavigator {
        return ActivityNavigator()
    }

    @Provides
    fun provideFragmentNavigator(context: Context): IFragmentNavigator {
        return FragmentNavigator(context)
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