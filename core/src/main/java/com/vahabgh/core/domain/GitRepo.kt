package com.vahabgh.core.domain

data class GitRepo (
    val id : String,
    val ownerName : String,
    val repoName : String,
    val createDate : String,
    val createDateInMillis : Long,
    val description : String?,
    val forkCount : String,
    val starCount : String,
    val repoUrl : String?
)