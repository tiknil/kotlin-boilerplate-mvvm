package com.tiknil.app.views.fragments

import com.tiknil.app.BR
import com.tiknil.app.R
import com.tiknil.app.core.views.BaseFragment
import com.tiknil.app.databinding.FragmentMainBinding
import com.tiknil.app.viewmodels.fragment.MainFragmentViewModel
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    @Inject
    lateinit var viewModel: MainFragmentViewModel

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

    override fun viewModel(): MainFragmentViewModel = viewModel

    override fun bindingVariable(): Int = BR.viewModel

    override fun layoutId(): Int = R.layout.fragment_main

    //endregion

    //region Inner classes or interfaces
    //endregion
}