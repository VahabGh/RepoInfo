package com.vahabgh.repoinfo.presentation.di

import com.apollographql.apollo.ApolloClient
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.core.interactors.GetReposUseCase
import com.vahabgh.core.interactors.SaveReposUseCase
import com.vahabgh.repoinfo.GetListOfRepoQuery
import com.vahabgh.repoinfo.framework.RepoListDataSource
import com.vahabgh.repoinfo.framework.RepoListInteractors
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepoListModule {

//    @Provides
//    @ActivityRetainedScoped
//    fun provideGetRepoQuery(): GetListOfRepoQuery {
//        return GetListOfRepoQuery("","")
//    }

//    @Provides
//    @ActivityRetainedScoped
//    fun provideDataSource(
//        apolloClient: ApolloClient,
//        query: GetListOfRepoQuery,
//        gitRepoDatabase: GitRepoDatabase
//    ): RepoListDataSource {
//        return RepoListDataSource(apolloClient, query,gitRepoDatabase)
//    }

//
//    @Provides
//    @Qualifiers.RepoList
//    @ActivityRetainedScoped
//    fun provideGitRepoRepository(dataSource: GitRepoDataSource): GitRepoRepository {
//        return GitRepoRepository(dataSource)
//    }

    @Provides
    @Qualifiers.RepoList
    @ActivityRetainedScoped
    fun provideGetReposUseCase(@Qualifiers.Main repository: GitRepoRepository): GetReposUseCase {
        return GetReposUseCase(repository)
    }

    @Provides
    @Qualifiers.RepoList
    @ActivityRetainedScoped
    fun provideSaveReposUseCase(@Qualifiers.Main repository: GitRepoRepository): SaveReposUseCase {
        return SaveReposUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepoListInteractors(@Qualifiers.RepoList getReposUseCase: GetReposUseCase,@Qualifiers.RepoList saveReposUseCase: SaveReposUseCase): RepoListInteractors {
        return RepoListInteractors(getReposUseCase,saveReposUseCase)
    }

}