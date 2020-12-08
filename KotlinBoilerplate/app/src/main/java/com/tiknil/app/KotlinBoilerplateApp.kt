package com.tiknil.app

import android.app.Application
import com.tiknil.app.coordinators.AppCoordinator
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KotlinBoilerplateApp : Application() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    companion object {
        lateinit var app: KotlinBoilerplateApp
    }

    @Inject
    lateinit var appCoordinator: AppCoordinator

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate() {

        super.onCreate()
        app = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public
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