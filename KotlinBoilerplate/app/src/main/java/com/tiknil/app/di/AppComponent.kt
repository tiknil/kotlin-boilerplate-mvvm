package com.tiknil.app.di

import com.tiknil.app.KotlinBoilerplateApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Compomente Dagger2 dell'app che fa da punto di riferimento per la dependency injection dell'app
 */

@Singleton
@Component(modules = [ServicesModule::class, AndroidInjectionModule::class, UiModule::class, ViewsModule::class])
interface AppComponent {

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

    fun inject(app: KotlinBoilerplateApp)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun applicationBind(application: KotlinBoilerplateApp): Builder

    }

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