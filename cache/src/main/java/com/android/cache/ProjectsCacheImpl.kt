package com.android.cache

import com.android.cache.db.ProjectsDatabase
import com.android.cache.mapper.CachedProjectMapper
import com.android.cache.model.Config
import com.android.data.model.ProjectEntity
import com.android.data.repository.ProjectsCache
import io.reactivex.*
import timber.log.Timber
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper): ProjectsCache {

    override fun clearProjects(): Completable {
        return Completable.defer{
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer{
            projectsDatabase.cachedProjectsDao().insertProjects(
                projects.map{
                    mapper.mapToCached(it)
                }
            )
            Completable.complete()
        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {

        return projectsDatabase.cachedProjectsDao().getProjects()
            .map{
                it.map{
                    Timber.i("getProjects %s", it)
                    mapper.mapFromCached(it)
                }
            }
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
            .map{
                it.map{
                    mapper.mapFromCached(it)
                }
            }
    }

    override fun setProjectAsBoookmarked(projectId: String): Completable {
        return Completable.defer{
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(true, projectId)
            Completable.complete()
        }
    }

    override fun setProjectAsNotBookmarked(projectID: String): Completable {
        return Completable.defer{
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(false, projectID)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return projectsDatabase.cachedProjectsDao().getProjects().isEmpty
            .map{
                !it
            }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer{
            projectsDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTIme = (60 * 10 * 1000).toLong()
        return projectsDatabase.configDao().getConfig()
            .onErrorReturn{Config(lastCacheTime = 0)}
            .toSingle(Config(lastCacheTime = 0L))
            .map{
                currentTime - it.lastCacheTime > expirationTIme
            }
    }
}