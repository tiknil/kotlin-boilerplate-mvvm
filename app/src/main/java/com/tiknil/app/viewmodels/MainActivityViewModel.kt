package com.tiknil.app.viewmodels

import android.util.Log
import androidx.fragment.app.Fragment
import com.tiknil.app_service.AppContainer
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivityViewModel(container: AppContainer): BaseViewModel(container), HasSupportFragmentInjector {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
    //endregion


    //region Custom accessors
    //endregion


    //region Public

    fun foo() {
        Log.d(this.javaClass.name, "foo")
        Log.d(this.javaClass.name, container.cacheService().foo)
    }

    //endregion

    //region Protected, without modifier
    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    //endregion

    //region Inner classes or interfaces
    //endregion
}