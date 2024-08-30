package com.paymob.themoviedb.feature.fragments.home_fragment.data.model.request

import com.google.gson.annotations.SerializedName

data class MoviesRequestDto(
    @SerializedName("primary_release_year")
    val primaryReleaseYear: Int = 2024,
    @SerializedName("sort_by")
    val sortBy: String = "vote_average.desc",
)
