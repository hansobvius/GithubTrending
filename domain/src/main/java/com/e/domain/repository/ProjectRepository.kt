package com.e.domain.repository

import com.e.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectRepository {

    fun getProjects(): Observable<List<Project>>

    fun bookmarkProject(projectId: String): Completable

    fun unbookmarkProject(projectId: String): Completable

    fun getBookmarkedProject(): Observable<List<Project>>
}