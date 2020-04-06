package com.tiknil.app.core.services

import dagger.MapKey

enum class ServicesType {
    NORMAL,
    DEMO
}

@MapKey
annotation class ServicesTypeKey(val value: ServicesType)