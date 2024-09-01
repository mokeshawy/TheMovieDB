package com.paymob.themoviedb.feature.fragments.home_fragment.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.paymob.themoviedb.core.api_services.MovieDbApiServices
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response.MovieData
import com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source.MoviesPagingDataSource
import com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source.PAGE_SIZE
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieDbApiServices: MovieDbApiServices,
) {


    operator fun invoke() =
        Pager(PagingConfig(PAGE_SIZE)) { MoviesPagingDataSource(movieDbApiServices) }
            .flow.flowOn(Dispatchers.IO).map { it.map { data -> data.toMovieDataEntity() } }


    private fun MovieData.toMovieDataEntity() = MovieUiModel(
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}