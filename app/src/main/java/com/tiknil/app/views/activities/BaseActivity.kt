package com.tiknil.app.views.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tiknil.app.viewmodels.BaseViewModel
import com.trello.rxlifecycle3.components.support.RxFragmentActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel> : RxFragmentActivity(), HasSupportFragmentInjector {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependecyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
        viewModel().onCreated()
    }


    override fun onDestroy() {
        viewModel().onDestroy()
        //clearActivityReferences()
        super.onDestroy()
    }

    override fun onResume() {
        //(applicationContext as AirSelfieApp).setCurrentActivity(this)
        viewModel().onViewAppear()
        super.onResume()
    }

    override fun onPause() {
        viewModel().onViewDisappear()
        //clearActivityReferences()
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        //EventBus.getDefault().register(this)
        viewModel().onStart()
    }

    override fun onStop() {
        super.onStop()
        //EventBus.getDefault().unregister(this)
        viewModel().onStop()
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public

    /**
     * Ritorna l'oggetto databinding
     *
     * @return l'oggetto databinding
     */
    fun getViewDataBinding() : T = mViewDataBinding

    /**
     * Ritorna l'oggetto ViewModel
     *
     * @return l'istanza del view model
     */
    abstract fun viewModel() : V

    /**
     * Ritorna il riferimento all'oggetto ViewDataBinding
     *
     * @return il riferimento all'oggetto ViewDataBinding
     */
    abstract fun bindingVariable(): Int

    /**
     * Ritorna il layout
     *
     * @return il resource id del layout dell'activity
     */
    @LayoutRes
    abstract fun layoutId(): Int

    //endregion

    //region Protected, without modifier

    /**
     * Esegue il binding tra gli observables custom della view, se necessario va eseguito l'override nelle classe figlie
     */
    protected fun setupBindings() {}

    //endregion

    //region Private

    /**
     * Crea il graph di dependecy
     */
    private fun performDependecyInjection() {
        AndroidInjection.inject(this)
    }

    /**
     * Esegue il binding tra il layout della view e il viewmodel. Chiama il metodoo setupBinding per gli ulteriori binding custom
     */
    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutId())
        this.mViewModel = if (mViewModel == null) viewModel() else mViewModel
        mViewDataBinding.setVariable(bindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        setupBindings()
    }

    //endregion


    //region Override methods and callbacks

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    //endregion

    //region Inner classes or interfaces
    //endregion
}