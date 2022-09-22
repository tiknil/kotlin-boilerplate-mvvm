package com.tiknil.app.views.fragments

import com.tiknil.app.BR
import com.tiknil.app.R
import com.tiknil.app.databinding.FragmentMainBinding
import com.tiknil.app.viewmodels.fragment.MainFragmentVM

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentVM>() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    var viewModel: MainFragmentVM = MainFragmentVM()

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun setupUI() {
        super.setupUI()
        
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

    override fun viewModel(): MainFragmentVM = viewModel

    override fun layoutId(): Int = R.layout.fragment_main

    //endregion

    //region Inner classes or interfaces
    //endregion
}