package com.android.remote

import android.util.Log
import com.android.data.model.ProjectEntity
import com.android.data.repository.ProjectsRemote
import com.android.remote.mapper.ProjectsResponseModelMapper
import com.android.remote.service.GithubTrendingService
import io.reactivex.Flowable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectsResponseModelMapper) : ProjectsRemote {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return service.searchRepositories("lenguage:kotlin", "stars", "desc")
            .map{
                it.items.map{
                    Log.i("request", it.toString())
                    mapper.mapFromModel(it)
                }
            }
    }
}