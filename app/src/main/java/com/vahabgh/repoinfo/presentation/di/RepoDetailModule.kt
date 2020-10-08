package com.vahabgh.repoinfo.presentation.di

import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.core.interactors.GetRepoByIdUseCase
import com.vahabgh.repoinfo.framework.RepoDetailInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepoDetailModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetRepoByIdUseCase(@Qualifiers.Main repository: GitRepoRepository): GetRepoByIdUseCase {
        return GetRepoByIdUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetRepoDetailInteractor(getRepoByIdUseCase: GetRepoByIdUseCase) : RepoDetailInteractors{
        return RepoDetailInteractors(getRepoByIdUseCase)
    }

}