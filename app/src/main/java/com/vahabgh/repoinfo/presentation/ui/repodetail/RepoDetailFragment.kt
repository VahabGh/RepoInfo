package com.vahabgh.repoinfo.presentation.ui.repodetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.vahabgh.repoinfo.BR
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.FragmentRepoDetailBinding
import com.vahabgh.repoinfo.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : BaseFragment<DetailRepoViewModel,FragmentRepoDetailBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_repo_detail

    override val viewModel: DetailRepoViewModel by viewModels()

    val args: RepoDetailFragmentArgs by navArgs()

    override fun bindObservables() {
        if (!viewModel.repo.hasObservers()){
            viewModel.repo.observe(viewLifecycleOwner, Observer {
                binding?.setVariable(BR.gitRepo,it)
                binding?.executePendingBindings()
            })
        }
    }

    override fun config() {
        viewModel.getRepoById(args.repoId)
    }

}