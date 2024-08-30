package com.paymob.themoviedb.feature.fragments.home_fragment.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import com.paymob.core.error.AppException
import com.paymob.core.utils.PagingListStateAdapter
import com.paymob.themoviedb.R
import com.paymob.themoviedb.core.moviedb_base_fragment.MovieDbBaseFragment
import com.paymob.themoviedb.databinding.FragmentHomeBinding
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieDataEntity
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.viewmodel.HomeViewModel
import com.paymob.themoviedb.feature.fragments.home_fragment.presentation.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : MovieDbBaseFragment<FragmentHomeBinding>() {

    override fun layoutInflater() = FragmentHomeBinding.inflate(layoutInflater)

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupViews()
    }

    private fun FragmentHomeBinding.setupViews() {
        setAppBarTitle()
        getPagingMovieDataEntity()
    }


    private fun FragmentHomeBinding.setAppBarTitle() = appBarView.setTitleBarTv(R.string.app_name)


    private fun FragmentHomeBinding.getPagingMovieDataEntity() {
        setUpMovieAdapter()
        viewModel.getPagingMovieDataEntity(submitPagingMovieEntity = {
            movieAdapter.submitData(it)
        })
    }


    private fun FragmentHomeBinding.setUpMovieAdapter() {
        movieAdapter = MovieAdapter(getMovieDataItemDiffCallback(), onItemClicked = {
            navigateToDetailsScreen(it)
        }, onFavoriteClicked = { id, isChecked ->
            //TODO HANDLE FAVORITE AND UN FAVORITE HERE
        })
        movieRV.adapter = getConcatLoadingAdapter()
    }


    private fun navigateToDetailsScreen(movieDataEntity: MovieDataEntity) {
        viewModel.setMovieDataEntity(movieDataEntity)
        //TODO NAVIGATE TO DETAILS SCREEN
    }

    private fun getConcatLoadingAdapter(): ConcatAdapter {
        val footerAdapter = footerAdapter()
        val headerAdapter = headerAdapter()
        movieAdapter.addLoadStateListener { loadState ->
            headerAdapter.loadState = loadState.refresh
            footerAdapter.loadState = loadState.append
        }
        return ConcatAdapter(headerAdapter, movieAdapter, footerAdapter)
    }

    private fun footerAdapter() =
        PagingListStateAdapter(onLoadStateError = { handleLoadStateError(it) }) {
            movieAdapter.retry()
        }


    private fun headerAdapter() =
        PagingListStateAdapter(onLoadStateError = { handleLoadStateError(it) }) {
            movieAdapter.retry()
        }


    private fun handleLoadStateError(loadState: LoadState.Error) =
        (loadState.error as? AppException)?.appError?.handleError { Timber.i(exception) }


    private fun getMovieDataItemDiffCallback() = object : DiffUtil.ItemCallback<MovieDataEntity>() {
        override fun areItemsTheSame(
            oldItem: MovieDataEntity, newItem: MovieDataEntity,
        ) = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: MovieDataEntity, newItem: MovieDataEntity,
        ) = oldItem == newItem
    }
}