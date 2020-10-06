package com.vahabgh.repoinfo.presentation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitRepoEntity(
    @ColumnInfo(name = "repo_id") val repoId : String,
    @ColumnInfo(name = "owner_name") val ownerName : String,
    @ColumnInfo(name = "repo_name") val repoName : String,
    @ColumnInfo(name = "create_date") val createDate : String,
    @ColumnInfo(name = "description") val description : String?,
    @ColumnInfo(name = "fork_count") val forkCount : Int,
    @ColumnInfo(name = "star_count") val starCount : Int,
    @ColumnInfo(name = "repo_url") val repoUrl : String?,
    @ColumnInfo(name = "page_index") val pageIndex : Int,
    @PrimaryKey(autoGenerate = true) val id : Int = 0
    )