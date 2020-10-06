package com.vahabgh.repoinfo.presentation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastPageEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "start") val startC: String,
    @ColumnInfo(name = "end") val endC: String
)