package com.vahabgh.core.domain

data class GitRepo (
    val id : String,
    val ownerName : String,
    val repoName : String,
    val createDate : String,
    val description : String?,
    val forkCount : String,
    val starCount : String,
    val collaborators: List<Collaborator>?,
    val repoUrl : String?
)