package com.vahabgh.repoinfo.presentation.di

import com.apollographql.apollo.ApolloClient
import com.vahabgh.core.data.GitRepoDataSource
import com.vahabgh.core.data.GitRepoRepository
import com.vahabgh.repoinfo.GetFirstListOfRepositoriesQuery
import com.vahabgh.repoinfo.GetListOfRepoQuery
import com.vahabgh.repoinfo.framework.GitRepoDataMapper
import com.vahabgh.repoinfo.framework.GitRepoDataSourceImpl
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Qualifier


@Module
@InstallIn(ActivityRetainedComponent::class)
object MainActivityModule {

    @Provides
    @Qualifiers.Main
    @ActivityRetainedScoped
    fun provideGetFirstRepoQuery(): GetFirstListOfRepositoriesQuery {
        return GetFirstListOfRepositoriesQuery()
    }

    @Provides
    @Qualifiers.Main
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
    @Qualifiers.Main
    @ActivityRetainedScoped
    fun provideDataSource(
        apolloClient: ApolloClient,
        gitRepoDatabase: GitRepoDatabase,
        @Qualifiers.Main query: GetListOfRepoQuery,
        @Qualifiers.Main queryFirst: GetFirstListOfRepositoriesQuery,
        @Qualifiers.Main mapper: GitRepoDataMapper
    ): GitRepoDataSource {
        return GitRepoDataSourceImpl(apolloClient,gitRepoDatabase, queryFirst,query,mapper)
    }


    @Provides
    @Qualifiers.Main
    @ActivityRetainedScoped
    fun provideGitRepoRepository(@Qualifiers.Main dataSource: GitRepoDataSource): GitRepoRepository {
        return GitRepoRepository(dataSource)
    }

}