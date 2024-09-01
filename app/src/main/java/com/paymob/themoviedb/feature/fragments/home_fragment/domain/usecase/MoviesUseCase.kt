package com.paymob.themoviedb.feature.fragments.home_fragment.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.paymob.themoviedb.core.api_services.MovieDbApiServices
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.MoviesDao
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.RemoteKeyDao
import com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source.MoviesPagingDataSource
import com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source.PAGE_SIZE
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.mapper.toMovieDataUiModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieDbApiServices: MovieDbApiServices,
    private val moviesDao: MoviesDao,
    private val remoteKeyDao: RemoteKeyDao,
) {


    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke() = Pager(
        PagingConfig(PAGE_SIZE),
        remoteMediator = MoviesPagingDataSource(
            movieDbApiServices = movieDbApiServices,
            moviesDao = moviesDao,
            remoteKeyDao = remoteKeyDao
        ),
        pagingSourceFactory = { moviesDao.getPagingMovieList() }).flow.map { it.map { data -> data.toMovieDataUiModel() } }


}