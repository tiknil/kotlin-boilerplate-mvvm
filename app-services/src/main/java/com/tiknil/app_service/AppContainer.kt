package com.tiknil.app_service

import android.content.Context
import com.tiknil.app_service.activitynavigator.IActivityNavigator
import com.tiknil.app_service.cache.ICacheService
import com.tiknil.app_service.fragmentnavigator.IFragmentNavigator
import dagger.Lazy
import javax.inject.Inject

class AppContainer @Inject constructor(
    private val contextLazy: Lazy<Context>,
    private val cacheServiceLazy: Lazy<ICacheService>,
    private val activityNavigatorLazy: Lazy<IActivityNavigator>,
    private val fragmentNavigatorLazy: Lazy<IFragmentNavigator>
)  {

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

    fun context(): Context = contextLazy.get()

    fun cacheService(): ICacheService = cacheServiceLazy.get()

    fun activityNavigator(): IActivityNavigator = activityNavigatorLazy.get()

    fun  fragmentNavigator(): IFragmentNavigator = fragmentNavigatorLazy.get()

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