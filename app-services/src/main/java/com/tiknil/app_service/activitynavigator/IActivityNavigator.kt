package com.tiknil.app_service.activitynavigator

import android.app.Activity
import android.os.Bundle

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
    fun openActivity(activity: Activity, activityToOpen: Class<Activity>, finish: Boolean, animation: ActivitySlideAnimation)

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
        activity: Activity,
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
        activity: Activity,
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
        activity: Activity,
        animation: ActivitySlideAnimation,
        onCompletion: Runnable?
    )
}