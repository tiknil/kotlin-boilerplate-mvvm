package com.tiknil.app.core.services

import android.content.Context

interface IAppContainer {

    fun context(): Context

    fun dataService(): IDataService

    fun restService(): IRestService
}