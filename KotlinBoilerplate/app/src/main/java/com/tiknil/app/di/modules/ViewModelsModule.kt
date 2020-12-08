package com.tiknil.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.di.ViewModelKey
import com.tiknil.app.viewmodels.activities.MainActivityViewModel
import com.tiknil.app.views.activities.MainActivity
import com.tiknil.app.services.AppContainer
import com.tiknil.app.viewmodels.fragment.MainFragmentViewModel
import com.tiknil.app.views.fragments.MainFragment
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

class ViewModelsModule {

    class ProvideViewModel {

        // Activity
        fun provideMainActivityViewModel(container: IAppContainer): ViewModel =
            MainActivityViewModel(container)

        // Fragment
        fun provideMainFragmentViewModel(container: IAppContainer): ViewModel =
            MainFragmentViewModel(container)
    }

    class InjectViewModel {

        // Activity
        fun provideMainActivityViewModel(
            factory: ViewModelProvider.Factory,
            target: MainActivity
        ) = ViewModelProvider(target, factory).get(MainActivityViewModel::class.java)

        // Fragment
        fun provideMainFragmentViewModel(
            factory: ViewModelProvider.Factory,
            target: MainFragment
        ) = ViewModelProvider(target, factory).get(MainFragmentViewModel::class.java)


    }
}