package com.vahabgh.repoinfo.presentation.ui.repolist

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.core.domain.PageInfo
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.FragmentReposBinding
import com.vahabgh.repoinfo.presentation.ui.MainActivity
import com.vahabgh.repoinfo.presentation.ui.base.BaseFragment
import com.vahabgh.repoinfo.presentation.ui.repolist.list.EndlessScrollListener
import com.vahabgh.repoinfo.presentation.ui.repolist.list.ReposAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*


@AndroidEntryPoint
class ReposFragment : BaseFragment<ReposViewModel, FragmentReposBinding>() {

    override val layoutRes: Int = R.layout.fragment_repos

    override val viewModel: ReposViewModel by viewModels()

    private var reposAdapter: ReposAdapter? = null

    private var pageIndex: Int = 0

    override fun bindObservables() {
        if (!viewModel.repo.hasObservers())
            viewModel.repo.observe(viewLifecycleOwner, Observer {
                setData(it.gitRepos)
                pageIndex++
            })

        if (!viewModel.pagination.hasObservers())
            viewModel.pagination.observe(viewLifecycleOwner, Observer {
                handlePagination(it)
            })
    }

    private fun handlePagination(paginateState: Int?) {

        when (paginateState) {
            ReposViewModel.IN_PAGINATION -> {

            }
            ReposViewModel.PAGINATION_DONE -> {
                reposAdapter?.removeFooter()
            }
            ReposViewModel.PAGINATION_ERROR -> {
                reposAdapter?.showErrorFooter("Operation failed")
            }
        }

    }

    private fun setData(items: List<Any>?) {
        if (reposAdapter == null) {
            reposAdapter = ReposAdapter(items as MutableList<Any>, ::onItemClick, ::retryDelegate)
            reposAdapter!!.addFooter()
            rv_repo.adapter = reposAdapter
        } else reposAdapter!!.addItems(items as MutableList<Any>)
    }

    override fun config() {
        addScrollListener()
        viewModel.getAllRepos(pageIndex)
    }

    private fun addScrollListener() {
        rv_repo.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                viewModel.getAllRepos(pageIndex)
            }
        })
    }

    private fun onItemClick(gitRepo: GitRepo) {
        (activity as? MainActivity)?.navigatToDetail(gitRepo.id)
    }

    private fun retryDelegate() {
        reposAdapter?.hideFooterError()
        viewModel.getAllRepos(pageIndex)

    }

}