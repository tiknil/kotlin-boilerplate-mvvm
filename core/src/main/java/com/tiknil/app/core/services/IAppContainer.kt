package com.tiknil.app.core.services

import android.content.Context

interface IAppContainer {

    fun context(): Context

    fun cacheService(): ICacheService

    fun activityNavigator(): IActivityNavigator

    fun  fragmentNavigator(): IFragmentNavigator
}