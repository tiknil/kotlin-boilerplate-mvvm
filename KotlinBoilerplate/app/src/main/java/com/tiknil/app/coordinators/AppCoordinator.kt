package com.tiknil.app.coordinators

import com.tiknil.app.core.services.IActivityReference
import com.tiknil.app.core.views.BaseActivity
import dagger.Lazy
import java.lang.ref.WeakReference
import javax.inject.Inject

class AppCoordinator @Inject constructor(
    private val onBoardingCoordinatorLazy: Lazy<OnBoardingCoordinator>
) : AbstractBaseCoordinator(), BaseCoordinatorDelegate, IActivityReference {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    private var activityWeakReference: WeakReference<BaseActivity<*, *>?> = WeakReference(null)

    var currentCoordinator: WeakReference<AbstractBaseCoordinator?> = WeakReference(null)

    //endregion


    //region Class methods
    //endregion


    //region Constructors / Lifecycle
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
        val onBoardingCoordinator = onBoardingCoordinatorLazy.get()
        onBoardingCoordinator.activityReferenceDelegate = this
        onBoardingCoordinator.start()
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