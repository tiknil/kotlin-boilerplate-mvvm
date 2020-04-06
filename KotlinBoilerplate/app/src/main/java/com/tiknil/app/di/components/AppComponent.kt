package com.tiknil.app.di.components

import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.di.modules.CoordinatorsModule
import com.tiknil.app.di.modules.ServicesModule
import com.tiknil.app.di.modules.UiModule
import com.tiknil.app.di.modules.ViewsModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Compomente Dagger2 dell'app che fa da punto di riferimento per la dependency injection dell'app
 */

@Singleton
@Component(modules = [ServicesModule::class, CoordinatorsModule::class, AndroidInjectionModule::class, UiModule::class, ViewsModule::class])
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