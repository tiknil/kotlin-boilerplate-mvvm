package com.tiknil.app.di

import androidx.lifecycle.ViewModel
import kotlin.reflect.KClass

annotation class ViewModelKey(
    val value: KClass<out ViewModel>
)