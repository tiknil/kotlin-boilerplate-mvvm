package com.tiknil.app.di.modules

import com.tiknil.app.di.modules.ViewModelsModule
import com.tiknil.app.views.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewsModule {

    // Activity

    @ContributesAndroidInjector(modules = [ViewModelsModule.InjectViewModel::class])
    abstract fun bind(): MainActivity
}