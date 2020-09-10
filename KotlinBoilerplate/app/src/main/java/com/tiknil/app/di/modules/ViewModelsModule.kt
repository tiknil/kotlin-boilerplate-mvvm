package com.tiknil.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiknil.app.di.ViewModelKey
import com.tiknil.app.viewmodels.MainActivityViewModel
import com.tiknil.app.views.activities.MainActivity
import com.tiknil.app.services.AppContainer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelsModule.ProvideViewModel::class])
class ViewModelsModule {

    // Activity

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(MainActivityViewModel::class)
        fun provideMainActivityViewModel(container: AppContainer): ViewModel =
            MainActivityViewModel(container)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideMainActivityViewModel(
            factory: ViewModelProvider.Factory,
            target: MainActivity
        ) = ViewModelProvider(target, factory).get(MainActivityViewModel::class.java)

    }
}