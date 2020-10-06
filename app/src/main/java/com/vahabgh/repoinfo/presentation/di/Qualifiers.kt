package com.vahabgh.repoinfo.presentation.di

import javax.inject.Qualifier

object Qualifiers {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Boot

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RepoList

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RepoDetail

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Main
}