package com.tiknil.app.di.modules

import com.tiknil.app.views.activities.MainActivity
import com.tiknil.app.views.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewsModule {

    // Activity

    @ContributesAndroidInjector(modules = [ViewModelsModule.InjectViewModel::class])
    abstract fun contributeMainActivity(): MainActivity

    // Fragment

    @ContributesAndroidInjector(modules = [ViewModelsModule.InjectViewModel::class])
    abstract fun contributeMainFragment(): MainFragment
}