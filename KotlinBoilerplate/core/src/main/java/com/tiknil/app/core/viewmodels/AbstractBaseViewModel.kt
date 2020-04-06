package com.tiknil.app.core.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.tiknil.app.core.services.IAppContainer
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractBaseViewModel (val container: IAppContainer) : ViewModel() {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    var needToSetupBindingChains = true

    /**
     * CompositeDisposable associati alla visualizzazione della view
     */
    protected var disposables = CompositeDisposable()

    /**
     * CompositeDisposable associati alla vita della view
     */
    protected var lifecycleDisposables = CompositeDisposable()

    var flowDelegate: Any? = null

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    /**
     * Metodo chiamato quando la view esegue il metodo onCreate, se necessario va eseguito l'override nelle classe figlie
     */
    open fun onCreated() {}

    /**
     * Metodo chiamato quando la view viene visualizzata, se necessario va eseguito l'override nelle classe figlie
     */
    open fun onViewAppear() {}

    /**
     * Metodo chiamato quando la view scompare, se necessario va eseguito l'override nelle classe figlie
     */
    open fun onViewDisappear() {}

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
     * Imposta il binding delle variabili RxKotlin, se necessario va eseguito l'override nelle classe figlie
     */
    open fun setupBindingChains() {}

    /**
     * Consente di settare dei parametri da utilizzare nella view, se necessario va eseguito l'override nelle classe figlie
     *
     * @param params parametri da utilizzare nella view
     */

    open fun setParams(params: Any?) {}

    /**
     * Ritorna la stringa localizzata
     *
     * @param resId l'id della stringa da localizzare
     */
    fun getString(@StringRes resId: Int) = container.context().getString(resId)

    //endregion

    //region Protected, without modifier

    /**
     * Visualizza il popup di conferma con le opzioni passate
     *
     * @param title       titolo del popup
     * @param message     messaggio del popup
     * @param confirmText testo del pulsante di destra per confermare
     * @param cancelText  testo del pulsante di sinistra per annullare
     * @param listener    listener per capire la scelta dell'utente
     */
    protected open fun showConfirmationPopup(title: String, message: String, confirmText: String, cancelText: String, listener: ConfirmationPopupListener) {}

    /**
     * Visualizza il popup con la sola opzione di cancel
     *
     * @param title       titolo del popup
     * @param message     messaggio del popup
     * @param cancelText  testo del pulsante di sinistra per annullare
     * @param listener    listener per capire la scelta dell'utente
     */
    protected open fun showCancelPopup(title: String, message: String, cancelText: String, listener: OnDismissListener? = null) {}


    //endregion

    //region Private

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces

    //endregion

    //region Inner classes or interfaces

    interface OnDismissListener {
        fun onDismiss()
    }

    interface ConfirmationPopupListener {
        fun positiveButtonClicked()
        fun negativeButtonClicked()
    }

    //endregion

}