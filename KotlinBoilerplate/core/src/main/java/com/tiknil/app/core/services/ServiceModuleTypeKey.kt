package com.tiknil.app.core.services


enum class ServicesType {
    NORMAL,
    DEMO
}

annotation class ServicesTypeKey(val value: ServicesType)