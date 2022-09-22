package com.tiknil.app

import android.app.Application
import com.tiknil.app.coordinators.AppCoordinator
import com.tiknil.app.di.AppInjector
import org.koin.core.context.startKoin
import timber.log.Timber

class KotlinBoilerplateApp : Application() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    companion object {
        lateinit var app: KotlinBoilerplateApp
    }

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

        performInjection()
        appCoordinator = AppInjector.getKoin().get()
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private

    /**
     * Esegue l'injection di tutta l'app
     */
    private fun performInjection() {
        AppInjector.initKoin()
    }

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion

}