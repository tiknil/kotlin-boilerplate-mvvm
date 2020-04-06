package com.tiknil.app

import android.app.Activity
import android.app.Application
import com.tiknil.app.coordinators.AppCoordinator
import com.tiknil.app.core.utils.TkLog
import com.tiknil.app.di.components.DaggerAppComponent
import com.tiknil.app.di.modules.ServicesModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class KotlinBoilerplateApp : Application(), HasActivityInjector {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    companion object {
        lateinit var app: KotlinBoilerplateApp
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

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

        performInjection()
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
        DaggerAppComponent.builder()
            .servicesModule(ServicesModule())
            .build()
            .inject(this)
    }

    //endregion


    //region Override methods and callbacks

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    //endregion

    //region Inner classes or interfaces
    //endregion

}