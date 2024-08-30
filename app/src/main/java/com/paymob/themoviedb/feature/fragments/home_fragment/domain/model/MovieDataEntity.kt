package com.paymob.themoviedb.feature.fragments.home_fragment.domain.model

data class MovieDataEntity(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String? = null,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var isFavorite: Boolean = false,
)
