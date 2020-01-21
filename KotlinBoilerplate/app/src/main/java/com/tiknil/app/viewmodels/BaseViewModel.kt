package com.tiknil.app.viewmodels

import com.tiknil.app.core.viewmodels.AbstractBaseViewModel
import com.tiknil.app.services.AppContainer
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(container: AppContainer): AbstractBaseViewModel(container) {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    /**
     * Metodo chiamato quando la view esegue il metodo onCreate, se necessario va eseguito l'override nelle classe figlie
     */
    override fun onCreated() {
        setupBindingChains()
        needToSetupBindingChains = false
    }


    /**
     * Metodo chiamato quando la view viene visualizzata, se necessario va eseguito l'override nelle classe figlie
     */
    override fun onViewAppear() {
        if (needToSetupBindingChains) {
            needToSetupBindingChains = false
            setupBindingChains()
        }
    }

    /**
     * Metodo chiamato quando la view scompare, se necessario va eseguito l'override nelle classe figlie
     */
    override fun onViewDisappear() {
        disposeDisposables()
        needToSetupBindingChains = true
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier

    /*protected void showConfirmationPopup(String title, String message, String confirmText, String cancelText, ConfirmationPopupListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityReference(), R.style.AirSelfieAlertDialogStyle);
        builder.setMessage(message)
            .setTitle(title)
            .setPositiveButton(confirmText, (dialogInterface, i) -> {
            if (listener != null) {
                listener.positiveButtonClicked();
            }
        })
        .setNegativeButton(cancelText, (dialogInterface, i) -> {
            if (listener != null) {
                listener.negativeButtonClicked();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        // TODO: impostare la dimensione nello style.xml
        TextView textView = dialog.findViewById(android.R.id.message);
        textView.setTextSize(14);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(14);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(14);

    }*/

    override fun showConfirmationPopup(
        title: String,
        message: String,
        confirmText: String,
        cancelText: String,
        listener: ConfirmationPopupListener
    ) {



    }

    //endregion

    //region Private

    /**
     * Esegue il dispose di tutti i disposables del view model
     */
    private fun disposeDisposables() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces

    //endregion
}