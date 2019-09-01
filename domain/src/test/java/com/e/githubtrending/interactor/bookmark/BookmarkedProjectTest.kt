package com.e.githubtrending.interactor.bookmark

import com.e.domain.executor.PostExecutionThread
import com.e.domain.interactor.bookmarked.BookmarkedProject
import com.e.domain.interactor.bookmarked.GetBookmarkedProjects
import com.e.domain.model.Project
import com.e.domain.repository.ProjectRepository
import com.e.githubtrending.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BookmarkedProjectTest {

    private lateinit var bookmarkProject: BookmarkedProject
    @Mock lateinit var projectsRepository: ProjectRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkedProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProject.buildUseCaseCompletable(
            BookmarkedProject.Params.forProject(ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(any()))
            .thenReturn(completable)
    }
}