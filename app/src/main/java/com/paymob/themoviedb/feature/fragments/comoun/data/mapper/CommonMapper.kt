package com.paymob.themoviedb.feature.fragments.comoun.data.mapper

import com.paymob.themoviedb.feature.fragments.comoun.data.model.MovieEntity
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response.MovieData


fun MovieData.toMovieEntity() = MovieEntity(
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = isFavorite,
)
