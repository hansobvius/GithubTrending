package com.android.data.mapper

import com.android.data.model.ProjectEntity
import com.e.domain.model.Project
import javax.inject.Inject

class ProjectMapper @Inject constructor(): EntityMapper<ProjectEntity, Project> {

    override fun mapFromEntity(entity: ProjectEntity): Project {
        return Project(entity.id, entity.name, entity.fullName,
            entity.starCount, entity.dateCreated, entity.ownerName,
            entity.ownerAvatar, entity.isBookmarked)
    }

    override fun mapToEntity(domain: Project): ProjectEntity {
        return ProjectEntity(domain.id, domain.fullName, domain.fullName,
            domain.starCount, domain.dateCreated, domain.ownerName,
            domain.ownerAvater, domain.isBookMarked)
    }
}