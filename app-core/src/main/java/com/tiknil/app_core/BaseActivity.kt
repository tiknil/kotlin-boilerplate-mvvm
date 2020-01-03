package com.tiknil.app_core

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle3.components.support.RxFragmentActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Activity astratta di base ereditata da tutte le activity che raggruppa le funzionalità comuni
 * e implementa l'impostazione di base con i view model e il binding con i componenti della view
 */

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel> : RxFragmentActivity(), HasSupportFragmentInjector {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V

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

    /**
     * Nasconde la tastiera
     */
    fun hideKeyboard() {
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    //endregion

    //region Protected, without modifier

    /**
     * Esegue il binding tra gli observables custom della view, se necessario va eseguito l'override nelle classe figlie
     */
    open fun setupBindings() {}

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
        this.mViewModel = if (!::mViewModel.isInitialized) viewModel() else mViewModel
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