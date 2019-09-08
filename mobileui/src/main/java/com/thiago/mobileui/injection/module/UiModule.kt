package com.thiago.mobileui.injection.module

import com.e.domain.executor.PostExecutionThread
import com.thiago.mobileui.browse.BrowseActivity
import com.thiago.mobileui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecuteThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity
}