package com.tiknil.app.services

import android.content.Context
import com.tiknil.app.core.services.IActivityNavigator
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.ICacheService
import com.tiknil.app.core.services.IFragmentNavigator
import dagger.Lazy
import javax.inject.Inject

class AppContainer @Inject constructor(
    private val contextLazy: Lazy<Context>,
    private val cacheServiceLazy: Lazy<ICacheService>,
    private val activityNavigatorLazy: Lazy<IActivityNavigator>,
    private val fragmentNavigatorLazy: Lazy<IFragmentNavigator>
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

    override fun context(): Context = contextLazy.get()

    override fun cacheService(): ICacheService = cacheServiceLazy.get()

    override fun activityNavigator(): IActivityNavigator = activityNavigatorLazy.get()

    override fun fragmentNavigator(): IFragmentNavigator = fragmentNavigatorLazy.get()

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