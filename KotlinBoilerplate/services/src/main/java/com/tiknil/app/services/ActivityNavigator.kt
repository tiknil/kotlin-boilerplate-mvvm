package com.tiknil.app.services

import android.content.Intent
import android.os.Bundle
import com.tiknil.app.services.R
import com.tiknil.app.core.views.BaseActivity
import com.tiknil.app.core.services.IActivityNavigator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ActivityNavigator : IActivityNavigator {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields
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

    /**
     * Avvia l'activity passata come classe
     *
     * @param activity          activity da cui lanciare l'intent
     * @param activityToOpen    classe da avviare
     * @param finish            true se termina l'activity, false altrimenti
     * @param animation         l'animazione con cui visualizzare l'activity
     */
    override fun openActivity(
        activity: BaseActivity<*, *>,
        activityToOpen: Class<*>,
        finish: Boolean,
        animation: IActivityNavigator.ActivitySlideAnimation
    ) {
        openActivity(activity, activityToOpen, finish, animation, null)
    }

    /**
     * Avvia l'activity passata come classe
     *
     * @param activity          activity da cui lanciare l'intent
     * @param activityToOpen    classe da avviare
     * @param finish            true se termina l'activity, false altrimenti
     * @param animation         l'animazione con cui visualizzare l'activity
     * @param data              parametri opzionali da inviare all'activity
     */
    override fun openActivity(
        activity: BaseActivity<*, *>,
        activityToOpen: Class<*>,
        finish: Boolean,
        animation: IActivityNavigator.ActivitySlideAnimation,
        data: Bundle?
    ) {
        val i = Intent(activity, activityToOpen)
        if (data != null) {
            i.putExtras(data)
        }
        i.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP //deve rimanere sempre una sola activity
        activity.startActivity(i)
        when (animation) {
            IActivityNavigator.ActivitySlideAnimation.NO_ANIMATION -> {
            }
            IActivityNavigator.ActivitySlideAnimation.SLIDE_BOTTOM_TO_TOP -> activity.overridePendingTransition(
                R.anim.activity_slide_in_up,
                R.anim.activity_slide_out_up
            )
            IActivityNavigator.ActivitySlideAnimation.SLIDE_TOP_TO_BOTTOM -> activity.overridePendingTransition(
                R.anim.activity_slide_in_down,
                R.anim.activity_slide_out_down
            )
        }
        if (finish) {
            activity.finish()
        }
    }

    /**
     * Chiude l'activity passata come argomento
     *
     * @param activity          l'activity da chiudere
     * @param animation         l'animazione con cui chiudere l'activity
     */
    override fun closeActivity(
        activity: BaseActivity<*, *>,
        animation: IActivityNavigator.ActivitySlideAnimation
    ) {
        closeActivity(activity, animation, null)
    }

    /**
     * Chiude l'activity passata come argomento ed esegue un'operazione alla chiusura
     *
     * @param activity          l'activity da chiudere
     * @param animation         l'animazione con cui chiudere l'activity
     * @param onCompletion      l'operazione da eseguire alla chiusura
     */
    override fun closeActivity(
        activity: BaseActivity<*, *>,
        animation: IActivityNavigator.ActivitySlideAnimation,
        onCompletion: Runnable?
    ) {
        activity.finish()
        when (animation) {
            IActivityNavigator.ActivitySlideAnimation.NO_ANIMATION -> {
            }
            IActivityNavigator.ActivitySlideAnimation.SLIDE_BOTTOM_TO_TOP -> activity.overridePendingTransition(
                R.anim.activity_slide_in_up,
                R.anim.activity_slide_out_up
            )
            IActivityNavigator.ActivitySlideAnimation.SLIDE_TOP_TO_BOTTOM -> activity.overridePendingTransition(
                R.anim.activity_slide_in_down,
                R.anim.activity_slide_out_down
            )
        }

        if (onCompletion != null) {
            var op = Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onCompletion.run() }
        }
    }

    //endregion

    //region Inner classes or interfaces
    //endregion
}