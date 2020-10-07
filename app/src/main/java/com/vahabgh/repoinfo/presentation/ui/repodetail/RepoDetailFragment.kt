package com.vahabgh.repoinfo.presentation.ui.repodetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.FragmentRepoDetailBinding
import com.vahabgh.repoinfo.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : BaseFragment<DetailRepoViewModel,FragmentRepoDetailBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_repo_detail

    override val viewModel: DetailRepoViewModel by viewModels()

    override fun bindObservables() {

    }

    override fun config() {
        handleBundle()
//        viewModel.getRepoById(repoId)
    }

    var repoId : String = ""

    private fun handleBundle() {
        if (arguments?.containsKey(ID) == true)
            repoId = requireArguments().getString(ID) ?: ""
    }

    companion object{

        const val ID = "id"

        fun newInstance(id : String) : RepoDetailFragment{
            return RepoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ID,id)
                }
            }
        }

    }

}