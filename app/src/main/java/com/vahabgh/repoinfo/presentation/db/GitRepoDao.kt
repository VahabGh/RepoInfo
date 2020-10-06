package com.vahabgh.repoinfo.presentation.db

import android.util.Log
import androidx.room.*

@Dao
interface GitRepoDao {

    @Query("SELECT * FROM GitRepoEntity")
    fun getAll(): List<GitRepoEntity>

    @Query("SELECT * FROM GitRepoEntity where page_index=:pageIndex")
    fun getAllByPageIndex(pageIndex: Int): List<GitRepoEntity>

    @Query("SELECT * FROM GitRepoEntity where repo_id=:id")
    fun getById(id: String): GitRepoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gitRepoEntity: GitRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastPage(lastPageEntity: LastPageEntity)

    @Query("UPDATE LastPageEntity set start=:start,`end`=:end where id=:id")
    fun update(id: Int, start: String, end: String)

    @Query("SELECT * FROM LastPageEntity where id=:id")
    fun getLastPage(id: Int): LastPageEntity

    @Transaction
    fun insertAll(entities: List<GitRepoEntity>) {
        entities.forEach {
            insert(it)
        }
    }
}