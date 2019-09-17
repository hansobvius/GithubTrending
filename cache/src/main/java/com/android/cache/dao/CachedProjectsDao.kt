package com.android.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.cache.db.ProjectConstants.DELETE_PROJECTS
import com.android.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import com.android.cache.db.ProjectConstants.QUERY_PROJECTS
import com.android.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARKED_STATUS
import com.android.cache.model.CachedProject
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
abstract class CachedProjectsDao {

    @Query(QUERY_PROJECTS)
    abstract fun getProjects(): Observable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(proects: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects():Flowable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARKED_STATUS)
    abstract fun setBookmarkedStatus(isBookmarked: Boolean,
                                     projectId: String)
}