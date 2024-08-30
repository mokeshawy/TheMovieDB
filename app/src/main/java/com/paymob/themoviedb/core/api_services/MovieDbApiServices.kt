package com.paymob.themoviedb.core.api_services

import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbApiServices {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("primary_release_year") primaryReleaseYear: Int,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
    ): Response<MoviesResponseDto>
}