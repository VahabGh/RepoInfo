package com.vahabgh.repoinfo.presentation.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GitRepoEntity::class,LastPageEntity::class], version = 1)
abstract class GitRepoDatabase : RoomDatabase() {
    abstract fun gitRepoDao(): GitRepoDao
}