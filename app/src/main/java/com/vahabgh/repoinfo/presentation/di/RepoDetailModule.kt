package com.vahabgh.repoinfo.presentation.di

import com.apollographql.apollo.ApolloClient
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.core.interactors.GetRepoByIdUseCase
import com.vahabgh.core.interactors.GetReposUseCase
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.framework.RepoDetailDataSource
import com.vahabgh.repoinfo.framework.RepoDetailInteractors
import com.vahabgh.repoinfo.framework.RepoListDataSource
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepoDetailModule {
//
//    @Provides
//    @ActivityRetainedScoped
//    fun provideDataSource(
//        gitRepoDatabase: GitRepoDatabase
//    ): RepoDetailDataSource {
//        return RepoDetailDataSource(gitRepoDatabase)
//    }

//    @Provides
//    @Qualifiers.RepoDetail
//    @ActivityRetainedScoped
//    fun provideGitRepoRepository(dataSource: GitRepoDataSource): GitRepoRepository {
//        return GitRepoRepository(dataSource)
//    }

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