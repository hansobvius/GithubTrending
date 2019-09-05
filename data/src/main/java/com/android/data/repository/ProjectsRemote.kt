package com.android.data.repository

import com.android.data.model.ProjectEntity
import io.reactivex.Flowable
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects(): Flowable<List<ProjectEntity>>
}