package com.vahabgh.repoinfo.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.presentation.ui.repodetail.RepoDetailFragment
import com.vahabgh.repoinfo.presentation.ui.repolist.ReposFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.handleCoroutineException
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment()

    }

    private fun setFragment() {
        supportFragmentManager.
        beginTransaction().
        add(R.id.fr_container, ReposFragment(),"Repos")
            .commitNow()
    }

    fun navigatToDetail(id: String) {
        try {

            supportFragmentManager.beginTransaction().
            add(R.id.fr_container, RepoDetailFragment.newInstance(id),"ReposDetail")
                .commitNow()
        } catch (exeption : Exception){
            exeption.printStackTrace()
        }
    }
}