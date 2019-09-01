package com.thiago.presentation.mapper

import com.e.domain.model.Project
import com.thiago.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor(): Mapper<ProjectView, Project> {

    override fun mapToview(type: Project): ProjectView {
        return ProjectView(type.id, type.name, type.fullName, type.starCount,
            type.dateCreated, type.ownerName, type.ownerAvater,
            type.isBookMarked)
    }
}