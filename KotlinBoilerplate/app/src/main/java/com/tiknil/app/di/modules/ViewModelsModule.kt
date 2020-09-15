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

@Module(includes = [ViewModelsModule.ProvideViewModel::class])
class ViewModelsModule {



    @Module
    class ProvideViewModel {

        // Activity

        @Provides
        @IntoMap
        @ViewModelKey(MainActivityViewModel::class)
        fun provideMainActivityViewModel(container: IAppContainer): ViewModel =
            MainActivityViewModel(container)

        // Fragment

        @Provides
        @IntoMap
        @ViewModelKey(MainFragmentViewModel::class)
        fun provideMainFragmentViewModel(container: IAppContainer): ViewModel =
            MainFragmentViewModel(container)
    }

    @Module
    class InjectViewModel {

        // Activity

        @Provides
        fun provideMainActivityViewModel(
            factory: ViewModelProvider.Factory,
            target: MainActivity
        ) = ViewModelProvider(target, factory).get(MainActivityViewModel::class.java)

        // Fragment

        @Provides
        fun provideMainFragmentViewModel(
            factory: ViewModelProvider.Factory,
            target: MainFragment
        ) = ViewModelProvider(target, factory).get(MainFragmentViewModel::class.java)


    }




}