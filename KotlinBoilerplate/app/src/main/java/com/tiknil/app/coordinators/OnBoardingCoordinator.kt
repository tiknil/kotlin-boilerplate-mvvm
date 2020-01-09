package com.tiknil.app.coordinators

import com.tiknil.app.core.services.IActivityNavigator
import com.tiknil.app.core.services.IActivityReference
import com.tiknil.app.core.services.ICoordinator
import com.tiknil.app.core.services.IFragmentNavigator
import com.tiknil.app.views.activities.MainActivity
import javax.inject.Inject

class OnBoardingCoordinator @Inject constructor(
    private val fragmentNavigator: IFragmentNavigator,
    private val activityNavigator: IActivityNavigator
) : ICoordinator {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    lateinit var activityReferenceDelegate: IActivityReference

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
        if (activityReferenceDelegate.activityReference != null) {
            activityNavigator.openActivity(
                activityReferenceDelegate.activityReference!!,
                MainActivity::class.java,
                true,
                IActivityNavigator.ActivitySlideAnimation.NO_ANIMATION
            )
        }
    }

    //endregion

    //region Inner classes or interfaces
    //endregion
}