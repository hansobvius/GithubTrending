package com.android.data.store

import com.android.data.repository.ProjectsDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
    private val projectsCacheDataStore: ProjectsCacheDataStore,
    private val projectsRemoteDataStore: ProjectsRemoteDataStore){

    open fun getDataStore(projectsCached: Boolean, cacheExpired: Boolean): ProjectsDataStore {
        return if(projectsCached && !cacheExpired){
            projectsCacheDataStore
        }else{
            projectsRemoteDataStore
        }
    }

    open fun getCachedDataStore(): ProjectsCacheDataStore{
        return projectsCacheDataStore
    }

    open fun getRemoteDataStore(): ProjectsRemoteDataStore{
        return projectsRemoteDataStore
    }
}