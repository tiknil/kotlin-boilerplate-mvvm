package com.tiknil.app.core.models


data class Optional<T>(val value: T?)
fun <T> T?.asOptional() = Optional(this)