package com.android.data

import com.android.data.mapper.ProjectMapper
import com.android.data.repository.ProjectsCache
import com.android.data.store.ProjectsDataStoreFactory
import com.e.domain.model.Project
import com.e.domain.repository.ProjectRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val factory: ProjectsDataStoreFactory): ProjectRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> {areCached, isExpired ->
                Pair(areCached, isExpired)
            })
            .flatMap{
                factory.getDataStore(it.first, it.second).getProjects()
            }
            .flatMap{projects ->
                factory.getCachedDataStore()
                    .saveProjects(projects)
                    .andThen(Observable.just(projects))
            }
            .map{
                it.map{
                    mapper.mapFromEntity(it)
                }
            }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsBokkmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProject(): Observable<List<Project>> {
        return factory.getCachedDataStore().getBookmarkedProjects()
            .map{
                it.map{mapper.mapFromEntity(it)}
            }
    }
}