package com.tiknil.app.di.modules

import com.tiknil.app.views.activities.MainActivity
import com.tiknil.app.views.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

abstract class ViewsModule {

    // Activity
    abstract fun contributeMainActivity(): MainActivity

    // Fragment
    abstract fun contributeMainFragment(): MainFragment
}