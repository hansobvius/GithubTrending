package com.e.githubtrending.store

import com.android.data.store.ProjectsCacheDataStore
import com.android.data.store.ProjectsDataStoreFactory
import com.android.data.store.ProjectsRemoteDataStore
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import kotlin.test.assertEquals

open class ProjectsDataStoreFactoryTest {

    private val cacheStore = mock<ProjectsCacheDataStore>()
    private val remoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectsDataStoreFactory(cacheStore, remoteStore)

    @Test
    fun getDataStoreReturnsStoreWhenCacheExpired(){
        assertEquals(remoteStore, factory.getDataStore(true, true))
    }

    @Test
    fun getDataStoreReturnsStoreWhenProjectsNotCache(){
        assertEquals(remoteStore, factory.getDataStore(false, false))
    }

    @Test
    fun getDataStoreReturnsCacheStore(){
        assertEquals(cacheStore, factory.getDataStore(true, false))
    }

    @Test
    fun getCacheDataStoreReturnsCacheStore(){
        assertEquals(cacheStore, factory.getCachedDataStore())
    }
}