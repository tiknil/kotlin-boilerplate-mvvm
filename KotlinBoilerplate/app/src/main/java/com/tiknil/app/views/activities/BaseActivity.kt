package com.tiknil.app.views.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tiknil.app.viewmodels.BaseVM
import com.trello.rxlifecycle4.components.support.RxFragmentActivity

/**
 * Activity astratta di base ereditata da tutte le activity che raggruppa le funzionalità comuni
 * e implementa l'impostazione di base con i view model e il binding con i componenti della view
 */

abstract class BaseActivity<T: ViewDataBinding, V: BaseVM> : RxFragmentActivity() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    protected lateinit var binding: T
    private lateinit var mViewModel: V

    private var needToSetupBinding = true

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        viewModel().onCreated()
    }


    override fun onDestroy() {
        viewModel().onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        if(needToSetupBinding) {
            needToSetupBinding = false
            setupBindings()
        }
        viewModel().onStart()
    }

    override fun onStop() {
        super.onStop()
        needToSetupBinding = true
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
    fun getViewDataBinding() : T = binding

    /**
     * Ritorna l'oggetto ViewModel
     *
     * @return l'istanza del view model
     */
    abstract fun viewModel() : V

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
     * Esegue il binding tra il layout della view e il viewmodel. Chiama il metodoo setupBinding per gli ulteriori binding custom
     */
    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId())
        this.mViewModel = if (!::mViewModel.isInitialized) viewModel() else mViewModel
        setupBindings()
        needToSetupBinding = false
    }

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}