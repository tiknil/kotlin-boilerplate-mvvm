package com.tiknil.app.core.services

import com.tiknil.app.core.views.BaseActivity
import com.tiknil.app.core.views.BaseFragment

interface IFragmentNavigator {

    enum class FragmentSlideAnimation {
        NO_ANIMATION,
        SLIDE_LEFT_TO_RIGHT,
        SLIDE_RIGHT_TO_LEFT,
        SLIDE_TOP_TO_BOTTOM,
        SLIDE_BOTTOM_TO_TOP
    }

    var currentFragment: BaseFragment<*, *>?

    /**
     * Mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    fun showFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     */
    fun showFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     */
    fun showFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun showFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?,
        onCompletion: Runnable?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any?,
        onCompletion: Runnable?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param currentActivity   l'activity corrente
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun resetStackAndShowFragment(
        currentActivity: BaseActivity<*, *>,
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any?,
        onCompletion: Runnable?
    )

    /**
     * Ritorna true se lo stack contiene il fragment del tipo passato, false altrimenti
     *
     * @param currentActivity       l'activity corrente
     * @param fragmentType          il tipo di fragment
     */
    fun stackContainsFragmentOfType(currentActivity: BaseActivity<*, *>, fragmentType: Class<*>): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param currentActivity   l'activity corrente
     */
    fun popFragment(currentActivity: BaseActivity<*, *>): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param currentActivity   l'activity corrente
     * @param animation         l'animazione con cui eseguire il fragment
     */
    fun popFragment(currentActivity: BaseActivity<*, *>, animation: FragmentSlideAnimation): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param currentActivity   l'activity corrente
     * @param animation         l'animazione con cui eseguire il fragment
     * @param params            i parametri da passare al fragment
     */
    fun popFragment(
        currentActivity: BaseActivity<*, *>,
        animation: FragmentSlideAnimation,
        params: Any?
    ): Boolean

    /**
     * Ritorna il numero di fragment nello stack
     * 
     * @param currentActivity   l'activity corrente
     */
    fun backstackCount(currentActivity: BaseActivity<*, *>): Int

}