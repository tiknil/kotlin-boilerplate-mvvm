package com.tiknil.app.core.services

import android.content.Context

interface IAppContainer {

    var isDemo: Boolean

    fun context(): Context

    fun dataService(): IDataService
}