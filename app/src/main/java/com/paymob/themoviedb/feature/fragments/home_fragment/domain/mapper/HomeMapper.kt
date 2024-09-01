package com.paymob.themoviedb.feature.fragments.home_fragment.domain.mapper

import com.paymob.themoviedb.feature.fragments.comoun.data.model.MovieEntity
import com.paymob.themoviedb.feature.fragments.home_fragment.domain.model.MovieUiModel


fun MovieEntity.toMovieDataUiModel() = MovieUiModel(
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