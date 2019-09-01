package com.e.domain.interactor.bookmarked

import com.e.domain.executor.PostExecutionThread
import com.e.domain.interactor.CompletableUseCase
import com.e.domain.repository.ProjectRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
    private val projectRepository: ProjectRepository,
    postExecutionThread: PostExecutionThread
    ) : CompletableUseCase<UnbookmarkProject.Params >(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params cant´t be null")
        return projectRepository.unbookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String){
        companion object{
            fun forProject(projectId: String): Params{
                return Params(projectId)
            }
        }
    }
}