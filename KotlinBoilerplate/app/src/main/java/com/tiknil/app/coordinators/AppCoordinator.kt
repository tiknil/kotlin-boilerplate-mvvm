package com.tiknil.app.coordinators

import com.tiknil.app.core.services.IActivityReference
import com.tiknil.app.core.services.ICoordinator
import com.tiknil.app.core.views.BaseActivity
import dagger.Lazy
import java.lang.ref.WeakReference
import javax.inject.Inject

class AppCoordinator @Inject constructor(
    private val onBoardingCoordinatorLazy: Lazy<OnBoardingCoordinator>
) : ICoordinator, IActivityReference {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    private var activityWeakReference: WeakReference<BaseActivity<*, *>?> = WeakReference(null)

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

    override fun start() {
        val onBoardingCoordinator = onBoardingCoordinatorLazy.get()
        onBoardingCoordinator.activityReferenceDelegate = this
        onBoardingCoordinator.start()
    }

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