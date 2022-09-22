package com.tiknil.app.coordinators

import com.tiknil.app.R
import com.tiknil.app.views.activities.IActivityReference
import com.tiknil.app.views.activities.BaseActivity
import com.tiknil.app.navigation.core.StackNavigator
import com.tiknil.app.navigation.core.stackNavigationController
import com.tiknil.app.navigation.extensions.crossFade
import java.lang.ref.WeakReference

class AppCoordinator : AbstractBaseCoordinator(), BaseCoordinatorDelegate,
    IActivityReference {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    private var activityWeakReference: WeakReference<BaseActivity<*, *>?> = WeakReference(null)

    private var currentCoordinator: WeakReference<AbstractBaseCoordinator?> = WeakReference(null)

    private lateinit var mainNavigator: StackNavigator

    private val mainCoordinator by lazy {
        MainCoordinator(mainNavigator)
    }

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle

    init {
        activityReferenceDelegate = this
    }

    //endregion


    //region Custom accessors
    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks

    // AbstractBaseCoordinator

    override fun start() {
        activityReference?.let { activity ->
            // Inizializzazione del mainNavigator
            mainNavigator = activity.stackNavigationController(
                R.id.mainFragmentLayout
            ).value.apply {
                transactionModifier = { crossFade() }
            }

            mainCoordinator.start()
        }
    }

    override fun back() {
        super.back()
    }

    // BaseCoordinatorDelegate
    /**
     * Implemetazione del delegate per il set del coordinator corrente
     */
    override fun setCurrentCoordinator(coordinator: AbstractBaseCoordinator) {
        currentCoordinator = WeakReference(coordinator)
    }

    // IActivityReference
    /**
     * Implemetazione del delegate per il recupero del riferimento all'activity
     */
    override var activityReference: BaseActivity<*, *>?
        get() = activityWeakReference.get()
        set(value) {
            activityWeakReference = WeakReference(value)
        }


    //endregion

    //region Inner classes or interfaces
    //endregion
}
