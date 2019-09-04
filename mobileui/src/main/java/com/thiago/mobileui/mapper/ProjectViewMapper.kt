package com.thiago.mobileui.mapper

import com.thiago.mobileui.model.Project
import com.thiago.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor(): ViewMapper<ProjectView, Project>{

    override fun mapFromView(presentation: ProjectView): Project {
        return Project(presentation.id, presentation.name, presentation.fullName, presentation.starCount,
            presentation.dateCreated, presentation.ownerName, presentation.ownerAvatar, presentation.isBookmarked)
    }
}