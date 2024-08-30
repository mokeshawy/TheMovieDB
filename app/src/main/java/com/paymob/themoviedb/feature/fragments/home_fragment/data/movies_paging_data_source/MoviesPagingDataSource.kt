package com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source

import androidx.paging.PagingState
import com.paymob.core.base_paging_source.BasePagingSource
import com.paymob.core.error.AppError
import com.paymob.core.error.AppException
import com.paymob.core.error.MoviesListException
import com.paymob.core.error.ResponseUnAuthorizedException
import com.paymob.themoviedb.core.api_services.MovieDbApiServices
import com.paymob.themoviedb.core.response_error_body.getErrorBody
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.request.MoviesRequestDto
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response.MovieData
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.response.MoviesResponseDto
import retrofit2.Response
import timber.log.Timber

const val PAGE_SIZE = 10

class MoviesPagingDataSource(
    private val movieDbApiServices: MovieDbApiServices,
    private val requestDto: MoviesRequestDto = MoviesRequestDto(),
) : BasePagingSource<Int, MovieData>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {

            val pageNumber = params.key ?: 1
            val response = movieDbApiServices.getMovies(
                primaryReleaseYear = requestDto.primaryReleaseYear,
                sortBy = requestDto.sortBy,
                page = pageNumber
            )

            when {
                response.getErrorBody()?.statusCode == 7 ->
                    LoadResult.Error(getUnAuthorizedException(response))

                else -> LoadResult.Page(
                    data = response.body()?.movieData.orEmpty(),
                    prevKey = null,
                    nextKey = getNextKey(response)
                )
            }

        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(AppException(AppError.E(exception = MoviesListException())))
        }

    }

    private fun getNextKey(response: Response<MoviesResponseDto>): Int? {
        val body = response.body()
        if (body == null || body.movieData.isEmpty()) return null
        return body.page + 1
    }


    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getUnAuthorizedException(response: Response<*>): AppException {
        val appError = AppError.E(
            exception = ResponseUnAuthorizedException(),
            logMessage = "Api request to url: ${response.raw().request.url}: failed with code ${response.code()}",
            extraData = response
        )
        return AppException(appError)
    }
}