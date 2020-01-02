package com.tiknil.app.di

import android.content.Context
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app_service.AppContainer
import com.tiknil.app_service.cache.CacheService
import com.tiknil.app_service.cache.ICacheService
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
        cacheService: Lazy<ICacheService>
    ): AppContainer = AppContainer(context, cacheService)

    @Provides
    @Singleton
    fun provideContext(): Context = KotlinBoilerplateApp.app.applicationContext

    @Provides
    @Singleton
    fun provideCacheService(): ICacheService = CacheService()

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