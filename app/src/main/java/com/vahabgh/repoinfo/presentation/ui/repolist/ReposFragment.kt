package com.vahabgh.repoinfo.presentation.ui.repolist

import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vahabgh.core.domain.GitRepo
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.FragmentReposBinding
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
                setData(it)
                pageIndex++
            })

        if (!viewModel.pagination.hasObservers())
            viewModel.pagination.observe(viewLifecycleOwner, Observer {
                handlePagination(it)
            })
    }

    private fun handlePagination(paginateState: Int?) {

        when (paginateState) {
            ReposViewModel.PAGINATION_DONE -> {
                reposAdapter?.removeFooter()
            }
            ReposViewModel.PAGINATION_ERROR -> {
                reposAdapter?.showErrorFooter("Operation failed")
            }
        }

    }

    private fun setData(items: List<Any>?) {
        if (rv_repo.adapter == null) {
            reposAdapter = ReposAdapter(items as MutableList<Any>, ::onItemClick, ::retryDelegate)
            reposAdapter!!.addFooter()
            rv_repo.adapter = reposAdapter
        } else {
            reposAdapter!!.addItems(items as MutableList<Any>)
        }

        noSortedState()
    }

    override fun config() {
        noSortedState()
        setSortClickListener()
        addScrollListener()
        if (viewModel.repo.value == null) {
            viewModel.getAllRepos(pageIndex)
        }
    }

    var sortState: SortState = SortState.NOTHING()

    private fun setSortClickListener() {
        iv_sort_asc.setOnClickListener {
            if (sortState == SortState.ASC()) return@setOnClickListener
            sortState = SortState.ASC()
            reposAdapter?.sortAscending()
            iv_sort_asc.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.selected_rounded_background)
            iv_sort_des.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.deselected_rounded_background)
            iv_sort_des.setColorFilter(iv_sort_asc.context.getColor(R.color.textColorSecondary))
            iv_sort_asc.setColorFilter(iv_sort_asc.context.getColor(R.color.colorBackground))
        }

        iv_sort_des.setOnClickListener {
            if (sortState == SortState.DES()) return@setOnClickListener
            sortState = SortState.DES()
            reposAdapter?.sortDescending()
            iv_sort_asc.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.deselected_rounded_background)
            iv_sort_des.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.selected_rounded_background)
            iv_sort_des.setColorFilter(iv_sort_asc.context.getColor(R.color.colorBackground))
            iv_sort_asc.setColorFilter(iv_sort_asc.context.getColor(R.color.textColorSecondary))
        }
    }

    private fun noSortedState(){
        sortState = SortState.NOTHING() // when a new page of data is came the list is not completely sorted
        iv_sort_asc.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.deselected_rounded_background)
        iv_sort_des.background = ContextCompat.getDrawable(iv_sort.context,R.drawable.deselected_rounded_background)

        iv_sort_des.setColorFilter(iv_sort_asc.context.getColor(R.color.textColorSecondary))
        iv_sort_asc.setColorFilter(iv_sort_asc.context.getColor(R.color.textColorSecondary))
    }

    private fun addScrollListener() {
        rv_repo.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                viewModel.getAllRepos(pageIndex)
            }
        })
    }

    private fun onItemClick(gitRepo: GitRepo) {
        ReposFragmentDirections.actionReposFragmentToRepoDetailFragment(repoId = gitRepo.id).let {
            findNavController().navigate(it)
        }
    }

    private fun retryDelegate() {
        reposAdapter?.hideFooterError()
        viewModel.getAllRepos(pageIndex)
    }


    sealed class SortState {

        class ASC : SortState(){
            override fun image() = R.drawable.ic_acs
        }
        class DES : SortState(){
            override fun image() = R.drawable.ic_desc
        }
        class NOTHING : SortState(){
            override fun image() = R.drawable.ic_sort
        }

        abstract fun image() : Int
    }

}