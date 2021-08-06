package com.tiknil.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiknil.app.core.services.IAppContainer
import com.tiknil.app.di.ViewModelKey
import com.tiknil.app.viewmodels.activities.MainActivityVM
import com.tiknil.app.views.activities.MainActivity
import com.tiknil.app.viewmodels.fragment.MainFragmentVM
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
        @ViewModelKey(MainActivityVM::class)
        fun provideMainActivityViewModel(container: IAppContainer): ViewModel =
            MainActivityVM(container)

        // Fragment

        @Provides
        @IntoMap
        @ViewModelKey(MainFragmentVM::class)
        fun provideMainFragmentViewModel(container: IAppContainer): ViewModel =
            MainFragmentVM(container)
    }

    @Module
    class InjectViewModel {

        // Activity

        @Provides
        fun provideMainActivityViewModel(
            factory: ViewModelProvider.Factory,
            target: MainActivity
        ) = ViewModelProvider(target, factory).get(MainActivityVM::class.java)

        // Fragment

        @Provides
        fun provideMainFragmentViewModel(
            factory: ViewModelProvider.Factory,
            target: MainFragment
        ) = ViewModelProvider(target, factory).get(MainFragmentVM::class.java)


    }




}