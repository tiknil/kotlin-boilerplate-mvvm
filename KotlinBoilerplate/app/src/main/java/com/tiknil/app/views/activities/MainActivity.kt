package com.tiknil.app.views.activities

import android.os.Bundle
import com.tiknil.app.BR
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.R
import com.tiknil.app.databinding.ActivityMainBinding
import com.tiknil.app.viewmodels.activities.MainActivityVM

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityVM>() {


    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    var viewModel: MainActivityVM = MainActivityVM()

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as KotlinBoilerplateApp).appCoordinator.activityReference = this

        super.onCreate(savedInstanceState)

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

    override fun viewModel(): MainActivityVM = viewModel

    override fun layoutId(): Int = R.layout.activity_main

    //endregion

    //region Inner classes or interfaces
    //endregion
}
