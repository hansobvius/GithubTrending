package com.thiago.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.domain.interactor.bookmarked.BookmarkedProject
import com.e.domain.interactor.bookmarked.UnbookmarkProject
import com.e.domain.interactor.browse.GetProjects
import com.e.domain.model.Project
import com.thiago.presentation.mapper.ProjectViewMapper
import com.thiago.presentation.model.ProjectView
import com.thiago.presentation.state.Resource
import com.thiago.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects,
    private val bookmarkedProject: BookmarkedProject,
    private val unbookmarkProject: UnbookmarkProject,
    private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    init{
        fetchProjects()
    }

    override fun onCleared() {
        getProjects.dispose()
        super.onCleared()
    }

    fun getProjects(): LiveData<Resource<List<ProjectView>>>{
        return liveData
    }

    fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getProjects?.execute(ProjectsSubscriber())
    }

    fun bookmarkedProject(projectId: String){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return bookmarkedProject.execute(BookmarkedProjectSubscriber(),
            BookmarkedProject.Params.forProject(projectId))
    }

    fun unbookmarkedProject(projectId: String){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return unbookmarkProject.execute(BookmarkedProjectSubscriber(),
            UnbookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber: DisposableObserver<List<Project>>(){
        override fun onComplete() {}

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                t.map{
                    mapper.mapToview(it)
                }, null)
            )
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

    inner class BookmarkedProjectSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                liveData.value?.data,
                null)
            )
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR,
                null,
                e.localizedMessage))
        }
    }

}