package com.tiknil.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiknil.app.di.AppViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

class UiModule {

    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory =
        AppViewModelFactory(providers)
}