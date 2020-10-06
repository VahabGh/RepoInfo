package com.vahabgh.repoinfo.presentation.app

import android.app.Application
import com.facebook.stetho.Stetho

open class RepoInfoApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}

