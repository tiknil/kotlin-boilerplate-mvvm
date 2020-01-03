package com.tiknil.app.views.activities

import android.os.Bundle
import com.tiknil.app.BR
import com.tiknil.app.R
import com.tiknil.app.core.views.BaseActivity
import com.tiknil.app.databinding.ActivityMainBinding
import com.tiknil.app.viewmodels.MainActivityViewModel
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {


    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    @Inject
    lateinit var viewModel: MainActivityViewModel

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }

    override fun viewModel(): MainActivityViewModel = viewModel

    override fun bindingVariable(): Int = BR.viewModel

    override fun layoutId(): Int = R.layout.activity_main

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
