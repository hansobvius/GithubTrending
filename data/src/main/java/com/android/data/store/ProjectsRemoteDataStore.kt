package com.android.data.store

import com.android.data.model.ProjectEntity
import com.android.data.repository.ProjectsDataStore
import com.android.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsRemoteDataStore @Inject constructor(private val projectsRemote: ProjectsRemote): ProjectsDataStore  {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn´t supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clear projects isn´t supported here...")
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Get Bookmarked projects isn´t supported here...")
    }

    override fun setProjectAsBokkmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Set Bookmarked projects isn´t supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Set not bookaerked projects isn´t supported here...")
    }
}