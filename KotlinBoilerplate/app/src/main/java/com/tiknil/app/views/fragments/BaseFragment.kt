package com.tiknil.app.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.tiknil.app.core.utils.ThreadUtils
import com.tiknil.app.viewmodels.BaseVM
import com.trello.rxlifecycle4.components.support.RxFragment
import java.text.DateFormat

/**
 * Fragment di base che racchiude le funzionalità comuni a tutti i fragment e predispone il link con il view model relativo
 */

abstract class BaseFragment<T: ViewDataBinding, V: BaseVM> : RxFragment() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    protected lateinit var binding: T
    private lateinit var mViewModel: V

    private val isThisFragmentTheCurrentVisible: Boolean
        get() {
            val fragments = parentFragmentManager.fragments
            return fragments[fragments.size - 1] === this
        }

    protected var isViewAppeared = false
    var params: Any? = null
        set(value) {
            field = value
            if (::mViewModel.isInitialized) {
                viewModel().setParams(value)
                field = null
            }
        }

    var flowDelegate: Any? = null
        set(value) {
            field = value
            if (::mViewModel.isInitialized) {
                viewModel().flowDelegate = value
            }
        }

    protected var keyboardModeResizingView = false
    protected var defaultTimeFormat: DateFormat? = null
    private var needToSetupBinding = true

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = viewModel()
        mViewModel.onCreated()
        binding.setVariable(bindingVariable(), mViewModel)
        binding.executePendingBindings()

        setupUI()
        setupBinding()

        needToSetupBinding = false
    }

    override fun onStart() {
        super.onStart()

        viewModel().onStart()
        if (!isViewAppeared) {
            if (isThisFragmentTheCurrentVisible) {
                viewModel().flowDelegate = flowDelegate
                viewModel().setParams(params)
                ThreadUtils.runOnCoroutineScope(viewLifecycleOwner.lifecycleScope) {
                    viewModel().onViewAppear()
                }
                isViewAppeared = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isViewAppeared = false
        needToSetupBinding = true
        viewModel().onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel().onDestroy()
    }

    /**
     * Metodo chiamato quando il fragment viene visualizzato
     */
    fun onViewAppear() {
        ThreadUtils.runOnUiThread {
            if (keyboardModeResizingView) {
                initKeyboardModeResizingView()
            }
            hideKeyboard()
            if(needToSetupBinding) {
                needToSetupBinding = false
                setupBinding()
            }

            if(!isViewAppeared) {
                viewModel().onViewAppear()
            }

            isViewAppeared = true
        }
    }

    /**
     * Metodo chiamato quando il fragment viene nascosto
     */
    fun onViewDisappear() {
        ThreadUtils.runOnUiThread {
            isViewAppeared = false
            resetKeyboardToStandardMode()
            hideKeyboard()
            if (::mViewModel.isInitialized) {
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
        if (activity != null && activity is com.tiknil.app.views.activities.BaseActivity<*, *>) {
            (activity as com.tiknil.app.views.activities.BaseActivity<*, *>).hideKeyboard()
        }
    }

    /**
     * Imposta il comportamento della tastiera
     */
    protected fun initKeyboardModeResizingView() {
        if (activity != null && !requireActivity().isFinishing) {
            requireActivity().window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }

    /**
     * Reimposta la tastiera al funzionamento standard
     */
    protected fun resetKeyboardToStandardMode() {
        if (activity != null && !requireActivity().isFinishing) {
            requireActivity().window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        }
    }
    //endregion

    //region Private

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}