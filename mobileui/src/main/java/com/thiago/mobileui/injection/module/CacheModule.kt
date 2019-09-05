package com.thiago.mobileui.injection.module

import android.app.Application
import com.android.cache.ProjectsCacheImpl
import com.android.cache.db.ProjectsDatabase
import com.android.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object{
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): ProjectsDatabase{
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}