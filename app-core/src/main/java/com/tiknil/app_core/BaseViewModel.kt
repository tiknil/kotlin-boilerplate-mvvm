package com.tiknil.app_core

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tiknil.app_core.interfaces.IAppContainer
import com.tiknil.app_core.interfaces.ICacheService
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel (val container: IAppContainer) : ViewModel() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    protected var disposables = CompositeDisposable()
    private var needToSetupBindingChains = true

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    /**
     * Metodo chiamato quando la view esegue il metodo onCreate, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onCreated() {
        setupBindingChains()
    }

    /**
     * Metodo chiamato quando la view viene visualizzata, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onViewAppear() {
        if (needToSetupBindingChains) {
            needToSetupBindingChains = false
            setupBindingChains()
        }
    }

    /**
     * Metodo chiamato quando la view scompare, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onViewDisappear() {
        disposeDisposables()
        needToSetupBindingChains = true
    }

    /**
     * Metodo chiamato alla chiamata onStart dell'activity/fragment del flusso standard, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onStart() {}

    /**
     * Metodo chiamato alla chiamata onStop dell'activity/fragment del flusso standard, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onStop() {}

    /**
     * Metodo chiamato alla chiamata onDestroy dell'activity/fragment del flusso standard, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onDestroy() {}

    //endregion


    //region Custom accessors
    //endregion


    //region Public

    /**
     * Imposta i dati iniziali della view, se necessario va eseguito l'override nelle classe figlie
     */
    open fun initData() {}

    /**
     * Imposta il binding delle variabili RxKotlin, se necessario va eseguito l'override nelle classe figlie
     */
    open fun setupBindingChains() {}

    /**
     * Consente di settare dei parametri da utilizzare nella view, se necessario va eseguito l'override nelle classe figlie
     *
     * @param params parametri da utilizzare nella view
     */

    open fun setParams(params: Any) {}

    //endregion

    //region Protected, without modifier

    /**
     * Ritorna il context dell'applicazione
     */
    protected fun getContext(): Context = container.context()

    /**
     * Ritorna il servizio di cache (singleton)
     */
    protected fun getCacheService(): ICacheService = container.cacheService()

    /**
     * Esegue il dispose di tutti i disposables del view model
     */
    protected fun disposeDisposables() {
        if (!disposables.isDisposed) {
            disposables.dispose()
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