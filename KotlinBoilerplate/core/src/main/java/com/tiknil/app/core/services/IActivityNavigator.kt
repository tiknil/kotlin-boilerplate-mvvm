package com.tiknil.app.core.services

import android.os.Bundle
import com.tiknil.app.core.views.BaseActivity

interface IActivityNavigator {

    enum class ActivitySlideAnimation {
        NO_ANIMATION,
        SLIDE_TOP_TO_BOTTOM,
        SLIDE_BOTTOM_TO_TOP
    }

    /**
     * Avvia l'activity passata come classe
     *
     * @param activity          activity da cui lanciare l'intent
     * @param activityToOpen    classe da avviare
     * @param finish            true se termina l'activity, false altrimenti
     * @param animation         l'animazione con cui visualizzare l'activity
     */
    fun openActivity(activity: BaseActivity<*, *>, activityToOpen: Class<*>, finish: Boolean, animation: ActivitySlideAnimation)

    /**
     * Avvia l'activity passata come classe
     *
     * @param activity          activity da cui lanciare l'intent
     * @param activityToOpen    classe da avviare
     * @param finish            true se termina l'activity, false altrimenti
     * @param animation         l'animazione con cui visualizzare l'activity
     * @param data              parametri opzionali da inviare all'activity
     */
    fun openActivity(
        activity: BaseActivity<*, *>,
        activityToOpen: Class<*>,
        finish: Boolean,
        animation: ActivitySlideAnimation,
        data: Bundle?
    )

    /**
     * Chiude l'activity passata come argomento
     *
     * @param activity          l'activity da chiudere
     * @param animation         l'animazione con cui chiudere l'activity
     */
    fun closeActivity(
        activity: BaseActivity<*, *>,
        animation: ActivitySlideAnimation
    )

    /**
     * Chiude l'activity passata come argomento ed esegue un'operazione alla chiusura
     *
     * @param activity          l'activity da chiudere
     * @param animation         l'animazione con cui chiudere l'activity
     * @param onCompletion      l'operazione da eseguire alla chiusura
     */
    fun closeActivity(
        activity: BaseActivity<*, *>,
        animation: ActivitySlideAnimation,
        onCompletion: Runnable?
    )
}