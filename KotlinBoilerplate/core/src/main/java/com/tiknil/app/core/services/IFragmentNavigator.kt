package com.tiknil.app.core.services

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
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     */
    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     */
    fun showFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        replace: Boolean,
        params: Any?
    )

    /**
     * Mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da avviare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param replace           true per rimpiazzare il fragment corrente, false altrimenti
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun showFragment(
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
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     */
    fun resetStackAndShowFragment(layoutId: Int, fragment: BaseFragment<*, *>)

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     */
    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     */
    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     */
    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        params: Any?,
        onCompletion: Runnable?
    )

    /**
     * Resetta lo stack dei fragment e mostra il fragment passato come argomento
     *
     * @param layoutId          il layoutId in cui mostrare il fragment
     * @param fragment          fragment da visualizzare
     * @param animation         l'animazione con cui visualizzare il fragment
     * @param params            i parametri da passare al fragment
     * @param onCompletion      operazione da eseguire al termine della transaction
     */
    fun resetStackAndShowFragment(
        layoutId: Int,
        fragment: BaseFragment<*, *>,
        animation: FragmentSlideAnimation,
        params: Any?,
        onCompletion: Runnable?
    )

    /**
     * Ritorna true se lo stack contiene il fragment del tipo passato, false altrimenti
     *
     * @param fragmentType          il tipo di fragment
     */
    fun stackContainsFragmentOfType(fragmentType: Class<*>): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     */
    fun popFragment(): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param animation         l'animazione con cui eseguire il fragment
     */
    fun popFragment(animation: FragmentSlideAnimation): Boolean

    /**
     * Esegue il pop del primo fragment presente nello stack
     *
     * @param animation         l'animazione con cui eseguire il fragment
     * @param params            i parametri da passare al fragment
     */
    fun popFragment(animation: FragmentSlideAnimation, params: Any?): Boolean

    /**
     * Ritorna il numero di fragment nello stack
     */
    fun backstackCount(): Int

}