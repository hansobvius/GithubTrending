package com.thiago.mobileui.injection.module

import com.android.data.ProjectsDataRepository
import com.e.domain.repository.ProjectRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectRepository
}