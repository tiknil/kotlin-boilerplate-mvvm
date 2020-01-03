package com.tiknil.app.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tiknil.app.utils.ThreadUtils
import com.tiknil.app.viewmodels.BaseViewModel
import com.tiknil.app.views.activities.BaseActivity
import com.tiknil.app_service.fragmentnavigator.IFragmentEvents
import com.trello.rxlifecycle3.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import java.text.DateFormat

/**
 * Fragment di base che racchiude le funzionalità comuni a tutti i fragment e predispone il link con il view model relativo
 */

abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel> : RxFragment(), IFragmentEvents {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V
    private lateinit var mRootView: View

    private val isThisFragmentTheCurrentVisible: Boolean
        get() {
            val fragments = fragmentManager!!.fragments
            return fragments[fragments.size - 1] === this
        }

    protected var isViewAppeared = false
    override var params: Any? = null
        set(value) {
            if (::mViewModel.isInitialized) {
                viewModel().setParams(value!!)
                field = null
            }
        }
    protected var keyboardModeResizingView = false
    protected var defaultTimeFormat: DateFormat? = null

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependecyInjection()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mRootView = mViewDataBinding.root

        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = viewModel()
        mViewModel.onCreated()
        mViewDataBinding.setVariable(bindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()

        setupUI()
        setupBinding()

        viewModel().initData()
    }

    override fun onStart() {
        super.onStart()

        viewModel().onStart()
        if (!isViewAppeared) {
            if (isThisFragmentTheCurrentVisible) {
                if (::mViewModel.isInitialized && params != null) {
                    viewModel().setParams(params!!)
                    this.params = null
                }

                ThreadUtils.runOnUiThread(viewLifecycleOwner.lifecycleScope) {
                    viewModel().onViewAppear()
                }

                isViewAppeared = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel().onStop()
    }

    /**
     * Metodo chiamato quando il fragment viene visualizzato
     */
    override fun onViewAppear() {
        ThreadUtils.runOnUiThread(viewLifecycleOwner.lifecycleScope) {
            if (keyboardModeResizingView) {
                initKeyboardModeResizingView()
            }
            isViewAppeared = true
            hideKeyboard()
            if(::mViewModel.isInitialized) {
                viewModel().onViewAppear()
            }
            resetKeyboardToStandardMode()
        }
    }

    /**
     * Metodo chiamato quando il fragment viene nascosto
     */
    override fun onViewDisappear() {
        isViewAppeared = false
        resetKeyboardToStandardMode()
        hideKeyboard()
        ThreadUtils.runOnUiThread(viewLifecycleOwner.lifecycleScope) {
            if(::mViewModel.isInitialized) {
                viewModel().onViewDisappear()
            }
        }
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public

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
     * Imposta la UI, va eseguito l'override nelle classe figlie
     */
    open protected fun setupUI() {}

    /**
     * Imposta il binding delle variabili RxJava, va eseguito l'override nelle classe figlie
     */
    open protected fun setupBinding() {}

    /**
     * Nasconde la tastiera se visualizzata
     */
    protected fun hideKeyboard() {
        if (activity != null && activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).hideKeyboard()
        }
    }

    /**
     * Imposta il comportamento della tastiera
     */
    protected fun initKeyboardModeResizingView() {
        if (activity != null && !activity!!.isFinishing) {
            activity!!.window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    /**
     * Reimposta la tastiera al funzionamento standard
     */
    protected fun resetKeyboardToStandardMode() {
        if (activity != null && !activity!!.isFinishing) {
            activity!!.window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        }
    }
    //endregion

    //region Private

    /**
     * Crea il graph di dependecy
     */
    private fun performDependecyInjection() {
        AndroidSupportInjection.inject(this)
    }

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}