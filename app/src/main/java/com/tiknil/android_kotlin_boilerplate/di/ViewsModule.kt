package com.tiknil.android_kotlin_boilerplate.di

import com.tiknil.android_kotlin_boilerplate.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewsModule {

    // Activity

    @ContributesAndroidInjector(modules = [ViewModelsModule.InjectViewModel::class])
    abstract fun bind(): MainActivity
}