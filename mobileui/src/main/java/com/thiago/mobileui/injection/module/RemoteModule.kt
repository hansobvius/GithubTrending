package com.thiago.mobileui.injection.module

import com.android.data.repository.ProjectsRemote
import com.android.remote.ProjectsRemoteImpl
import com.android.remote.service.GithubTrendingService
import com.android.remote.service.GithubTrendingServiceFactory
import com.thiago.mobileui.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object{
        @Provides
        @JvmStatic
        fun providesGithubService(): GithubTrendingService{
            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}