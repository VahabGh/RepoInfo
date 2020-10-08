package com.vahabgh.repoinfo.presentation.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vahabgh.repoinfo.presentation.app.HiltRepoInfoApp
import com.vahabgh.repoinfo.presentation.db.GitRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(app: HiltRepoInfoApp): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideGitRepoDatabase(@ApplicationContext applicationContext: Context): GitRepoDatabase {
        return Room.databaseBuilder(
            applicationContext,
            GitRepoDatabase::class.java, "GitRepoDB"
        ).build()
    }

}