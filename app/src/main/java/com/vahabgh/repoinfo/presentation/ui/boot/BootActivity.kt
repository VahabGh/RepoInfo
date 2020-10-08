package com.vahabgh.repoinfo.presentation.ui.boot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Observer
import com.vahabgh.repoinfo.BR
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.ActivityBootBinding
import com.vahabgh.repoinfo.presentation.ui.MainActivity
import com.vahabgh.repoinfo.presentation.util.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_boot.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

@AndroidEntryPoint
class BootActivity : AppCompatActivity() {

    private val ISFIRSTINSTALL = "isFirstInstall"

    private val viewModel: BootViewModel by viewModels()

    private var binding: ActivityBootBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_boot)
        initBinding()
        bindObservable()
        setRetryButtonClickListener()
        boot()
    }

    private fun initBinding() {
        binding?.apply {
            lifecycleOwner = this@BootActivity
            setVariable(BR.vm, viewModel)
            executePendingBindings()
        }
    }

    private fun setRetryButtonClickListener() {
        btn_retry.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun boot() {
        val isFirstInstall = isFirstInstall()
        if (!isFirstInstall)
            viewModel.boot()
        else bootDoneOperation()
    }

    private fun bindObservable() {
        if (!viewModel.isBootDone.hasObservers())
            viewModel.isBootDone.observe(this, Observer { isBootDone ->
                if (isBootDone) {
                    setFirstInstallTrue()
                    bootDoneOperation()
                }
            })
    }

    private fun bootDoneOperation() {
        navigateToMainActivity()
        finish()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isFirstInstall(): Boolean {
        return Preferences.preferences(applicationContext).getBoolean(ISFIRSTINSTALL, false)
    }

    private fun setFirstInstallTrue() {
        Preferences.preferences(applicationContext).edit().putBoolean(ISFIRSTINSTALL, true).apply()
    }

}