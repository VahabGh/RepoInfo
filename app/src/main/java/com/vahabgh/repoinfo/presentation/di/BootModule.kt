package com.vahabgh.repoinfo.presentation.di

import com.apollographql.apollo.ApolloClient
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.core.interactors.GetFirstReposUseCase
import com.vahabgh.core.interactors.SaveReposUseCase
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.GetListOfRepoQuery
import com.vahabgh.repoinfo.framework.BootInteractors
import com.vahabgh.repoinfo.framework.GitRepoDataMapper
import com.vahabgh.repoinfo.framework.GitRepoDataSourceImpl
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
object BootModule {

    @Provides
    @Qualifiers.Boot
    @ActivityRetainedScoped
    fun provideGetFirstRepoQuery(): GetFirstListOfRepositoriesQuery {
        return GetFirstListOfRepositoriesQuery()
    }

    @Provides
    @Qualifiers.Boot
    @ActivityRetainedScoped
    fun provideGetRepoQuery(): GetListOfRepoQuery {
        return GetListOfRepoQuery("","")
    }

    @Provides
    @Qualifiers.Main
    @ActivityRetainedScoped
    fun provideGitDataMapper(): GitRepoDataMapper {
        return GitRepoDataMapper()
    }

    @Provides
    @Qualifiers.Boot
    @ActivityRetainedScoped
    fun provideDataSource(
        apolloClient: ApolloClient,
        gitRepoDatabase: GitRepoDatabase,
        @Qualifiers.Boot query: GetListOfRepoQuery,
        @Qualifiers.Boot queryFirst: GetFirstListOfRepositoriesQuery,
        @Qualifiers.Boot mapper: GitRepoDataMapper
    ): GitRepoDataSource {
        return GitRepoDataSourceImpl(apolloClient,gitRepoDatabase, queryFirst,query,mapper)
    }

    @Provides
    @Qualifiers.Boot
    @ActivityRetainedScoped
    fun provideGitRepoRepository(@Qualifiers.Boot dataSource: GitRepoDataSource) : GitRepoRepository {
        return GitRepoRepository(dataSource)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetFirstRepoUseCase(@Qualifiers.Boot repository: GitRepoRepository): GetFirstReposUseCase {
        return GetFirstReposUseCase(repository)
    }

    @Provides
    @Qualifiers.Boot
    @ActivityRetainedScoped
    fun provideSaveReposUseCase(@Qualifiers.Boot repository: GitRepoRepository): SaveReposUseCase {
        return SaveReposUseCase(repository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideBootInteractors(getFirstReposUseCase: GetFirstReposUseCase,@Qualifiers.Boot saveReposUseCase: SaveReposUseCase) : BootInteractors{
        return BootInteractors(getFirstReposUseCase,saveReposUseCase)
    }


}