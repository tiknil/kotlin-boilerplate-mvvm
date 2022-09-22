package com.tiknil.app.di

import com.tiknil.app.KotlinBoilerplateApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.mp.KoinPlatformTools
import timber.log.Timber

object AppInjector {


    //region Inner enums
    //endregion

    //region Constants
    //endregion

    //region Instance Fields

    @Suppress("VARIABLE_IN_SINGLETON_WITHOUT_THREAD_LOCAL")
    private var allDefaultModules = defaultModules()

    //endregion

    //region Class methods
    //endregion

    //region Constructors / Lifecycle
    //endregion

    //region Custom accessors
    //endregion

    //region Public

    /**
     * Inizializzazione di koin con i moduli di default
     */
    fun initKoin() {
        initKoinWithAllDefaultModules()
    }

    /**
     * Recupera l'istanza di koin, se non esiste allora viene creata con i moduli di default
     */
    fun getKoin(): Koin {
        // Recupero dell'istanza di koin dal context di default di Koin
        if(KoinPlatformTools.defaultContext().getOrNull() == null) {
            // Se non è presente l'istanza viene creata con la configurazione di default
            Timber.e("Koin non inizializzato - Inizializzazione con i moduli di default")
            initKoinWithAllDefaultModules()
        }
        return KoinPlatformTools.defaultContext().get()
    }

    /**
     * Imposta i moduli di default
     */
    fun setDefaultModules() {
        try {
            unloadKoinModules(demoModules())
            loadKoinModules(allDefaultModules)
        } catch (exception: Exception) {
            Timber.e("Errore durante il load dei moduli default - Errore: ${exception.message}")
            exception.printStackTrace()
        }
    }

    /**
     * Imposta i moduli demo
     */
    fun setDemoModules() {
        try {
            unloadKoinModules(allDefaultModules)
            loadKoinModules(demoModules())
        } catch(exception: Exception) {
            Timber.e("Errore durante il load dei moduli demo - Errore: ${exception.message}")
            exception.printStackTrace()
        }
    }

    //endregion

    //region Protected, without modifier
    //endregion

    //region Private

    private fun initKoinWithAllDefaultModules() {
        startKoin {
            androidContext(KotlinBoilerplateApp.app)
            modules(coordinatorsModule())
            modules(allDefaultModules)
        }
    }

    //endregion

    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion
}

