package com.vahabgh.core.domain

data class GitRepo (
    val id : String,
    val ownerName : String,
    val repoName : String,
    val createDate : String,
    val createDateInMillis : Long,
    val description : String?,
    val forkCount : Int,
    val starCount : Int,
    val collaborators: List<Collaborator>?,
    val repoUrl : String?
) /*: Comparable<GitRepo> {
    override fun compareTo(other: GitRepo): Int {
        return when {
            createDateInMillis > other.createDateInMillis -> 1
            createDateInMillis < other.createDateInMillis -> -1
            else -> 0
        }
    }
}*/