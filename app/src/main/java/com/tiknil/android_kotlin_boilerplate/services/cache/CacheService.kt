package com.tiknil.android_kotlin_boilerplate.services.cache

import android.util.Log

class CacheService: ICacheService {

    //region Inner enums
    //endregion


    //region Constants
    //endregion


    //region Instance Fields

    override var foo: String
        get() = "Pollo"
        set(value) {}

    //endregion


    //region Class methods
    //endregion

    //region Constructors / Lifecycle

    init {
        Log.d(this.javaClass.name, "CacheService init")
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
    //endregion

    //region Inner classes or interfaces
    //endregion
}