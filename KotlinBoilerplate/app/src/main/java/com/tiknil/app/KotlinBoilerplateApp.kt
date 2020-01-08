package com.tiknil.app

import android.app.Activity
import android.app.Application
import com.tiknil.app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
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

    var currentActivity: Activity? = null

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate() {

        super.onCreate()
        app = this

        DaggerAppComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)
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

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    //endregion

    //region Inner classes or interfaces
    //endregion

}