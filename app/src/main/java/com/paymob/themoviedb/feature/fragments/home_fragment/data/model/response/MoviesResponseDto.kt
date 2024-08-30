package com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response


import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieData: List<MovieData>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)