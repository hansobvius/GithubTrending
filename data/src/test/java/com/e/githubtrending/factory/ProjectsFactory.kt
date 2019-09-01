package com.e.githubtrending.factory

import com.android.data.model.ProjectEntity
import com.e.domain.model.Project
import com.e.githubtrending.factory.DataFactory

object ProjectsFactory {

    fun makeProjectEntity(): ProjectEntity{
        return ProjectEntity(
            DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomBoolean())
    }

    fun makeProject(): Project{
        return Project(
            DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomBoolean())
    }
}