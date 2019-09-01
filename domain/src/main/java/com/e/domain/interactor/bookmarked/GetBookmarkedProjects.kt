package com.e.domain.interactor.bookmarked

import com.e.domain.executor.PostExecutionThread
import com.e.domain.interactor.ObservableUseCase
import com.e.domain.model.Project
import com.e.domain.repository.ProjectRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetBookmarkedProjects @Inject constructor(
    private val projectRepository: ProjectRepository,
    postExecutionThread: PostExecutionThread): ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectRepository.getBookmarkedProject()
    }
}