package com.tiknil.app.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.tiknil.app.KotlinBoilerplateApp
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.core.services.IDataService
import com.tiknil.app.core.services.IRestService
import com.tiknil.app.di.AppInjector
import io.reactivex.rxjava3.disposables.CompositeDisposable

// Delegate che definisce dei metodi comuni a tutti i FlowDelegate
interface BaseFlowDelegate {
}

open class BaseVM {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    private var container: IAppContainer = AppInjector.getKoin().get()

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
    open fun onCreated() {
        setupBindingChains()
        needToSetupBindingChains = false
    }

    /**
     * Metodo chiamato quando la view viene visualizzata, se necessario va eseguito l'override nelle classe figlie
     */
    open fun onViewAppear() {
        if (needToSetupBindingChains) {
            needToSetupBindingChains = false
            disposables = CompositeDisposable()
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

    open fun onStop() {
        disposeDisposables()
        needToSetupBindingChains = true
    }

    /**
     * Metodo chiamato alla chiamata onDestroy dell'activity/fragment del flusso standard, se necessario va eseguito l'override nelle classe figlie
     */

    open fun onDestroy() {
        disposeLifecycledisposables()
    }

    //endregion


    //region Custom accessors

    /**
     * Imposta i servizi di default
     */
    protected fun setDefaultModules() {
        AppInjector.setDefaultModules()
        container = AppInjector.getKoin().get()
    }

    /**
     * Imposta i servizi di demo
     */
    protected fun setDemoModules() {
        AppInjector.setDemoModules()
        container = AppInjector.getKoin().get()
    }

    fun context(): Context = container.context()

    fun dataService(): IDataService = container.dataService()

    fun restService(): IRestService = container.restService()

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
    fun getString(@StringRes resId: Int) = context().getString(resId)

    //endregion

    //region Protected, without modifier

    /**
     * Visualizza un popup con due pulsanti
     *
     * @param title: il titolo del popup
     * @param message: il messaggio del popup
     * @param confirmText: il testo del pulsante di conferma
     * @param cancelText: il testo del pulsante di cancellazione
     * @param listener: il listener dei pulsanti
     */
    protected fun showConfirmationPopup(
        title: String,
        message: String,
        confirmText: String,
        cancelText: String,
        listener: ConfirmationPopupListener
    ) {
        val builder =
            AlertDialog.Builder((context() as KotlinBoilerplateApp).appCoordinator.activityReference as Context)
        builder.setMessage(message)
            .setTitle(title)
            .setPositiveButton(confirmText) { _, _ ->
                listener.positiveButtonClicked()
            }
            .setNegativeButton(cancelText) { _, _ ->
                listener.negativeButtonClicked()
            }

        val dialog = builder.create()
        dialog.show()
        customizeDialogUI(dialog)
    }

    /**
     * Visualizza un popup con un pulsanti
     *
     * @param title: il titolo del popup
     * @param message: il messaggio del popup
     * @param cancelText: il testo del pulsante di cancellazione
     * @param listener: il listener del pulsante
     */
    protected fun showCancelPopup(
        title: String,
        message: String,
        cancelText: String,
        listener: OnDismissListener?
    ) {
        val builder =
            AlertDialog.Builder((context() as KotlinBoilerplateApp).appCoordinator.activityReference as Context)
        builder.setMessage(message)
            .setTitle(title)
            .setPositiveButton(cancelText) { _, _ ->
                listener?.onDismiss()
            }

        val dialog = builder.create()
        dialog.show()
        customizeDialogUI(dialog)
    }

    /**
     * Verifica la connessione internet
     */
    @Suppress("DEPRECATION")
    protected fun isNetworkConnected(): Boolean {
        var result = false
        val connectivityManager =
            context().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }

    //endregion

    //region Private

    /**
     * Customizzazione del dialog
     */
    private fun customizeDialogUI(dialog: AlertDialog) {
        // TODO: Customizzazione del popup se necessario
//        val fontSize =
//            container.context().resources.getDimension(R.dimen.font_size_16) / container.context().resources.displayMetrics.scaledDensity
//
//        // Messaggio
//        val textView =
//            dialog.findViewById<androidx.appcompat.widget.AppCompatTextView>(android.R.id.message)
//        textView?.apply {
//            typeface = ResourcesCompat.getFont(context, R.font.avenir_roman)
//            textSize = fontSize
//        }
//
//        // Pulsanti
//        val medium = ResourcesCompat.getFont(container.context(), R.font.avenir_medium)
//        val mediumBold = Typeface.create(medium, Typeface.BOLD)
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).typeface = mediumBold
//        dialog.getButton(DialogInterface.BUTTON_POSITIVE).textSize = fontSize
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).typeface = medium
//        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).textSize = fontSize
    }

    /**
     * Esegue il dispose di tutti i disposables del view model
     */
    private fun disposeDisposables() {
        if (!disposables.isDisposed) {
            disposables.clear()
        }
    }

    /**
     * Esegue il dispose dei lifecycleDisposables del view model
     */
    private fun disposeLifecycledisposables() {
        if (!lifecycleDisposables.isDisposed) {
            lifecycleDisposables.clear()
        }
    }

    //endregion


    //region Override methods and callbacks

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