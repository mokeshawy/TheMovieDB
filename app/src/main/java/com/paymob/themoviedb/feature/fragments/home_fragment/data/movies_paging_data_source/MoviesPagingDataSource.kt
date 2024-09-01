package com.paymob.themoviedb.feature.fragments.home_fragment.data.movies_paging_data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.paymob.themoviedb.core.api_services.MovieDbApiServices
import com.paymob.themoviedb.feature.fragments.comoun.data.mapper.toMovieEntity
import com.paymob.themoviedb.feature.fragments.comoun.data.model.MovieEntity
import com.paymob.themoviedb.feature.fragments.comoun.data.model.RemoteKeyEntity
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.MoviesDao
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.RemoteKeyDao
import com.paymob.themoviedb.feature.fragments.home_fragment.data.model.request.MoviesRequestDto
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

const val PAGE_SIZE = 10
const val STATIC_REMOTE_KEY_ID = "REMOTE_ID"

@OptIn(ExperimentalPagingApi::class)
class MoviesPagingDataSource(
    private val movieDbApiServices: MovieDbApiServices,
    private val requestDto: MoviesRequestDto = MoviesRequestDto(),
    private val moviesDao: MoviesDao,
    private val remoteKeyDao: RemoteKeyDao,
) : RemoteMediator<Int, MovieEntity>() {


    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieEntity>,
    ): MediatorResult {

        return try {


            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.getKeyByMovie(STATIC_REMOTE_KEY_ID)
                        ?: return MediatorResult.Success(true)
                    if (remoteKey.nextPage == null) return MediatorResult.Success(true)
                    remoteKey.nextPage
                }
            }

            val response = movieDbApiServices.getMovies(
                primaryReleaseYear = requestDto.primaryReleaseYear,
                sortBy = requestDto.sortBy,
                page = page
            )

            val movieData = response.body()?.movieData.orEmpty()

            if (loadType == LoadType.REFRESH) {
                moviesDao.clearAll()
            }


            val nextPage = if (movieData.isEmpty()) null else page + 1
            remoteKeyDao.insertKey(setRemoteKey(nextPage))

            val movieEntity = response.body()?.movieData?.map { it.toMovieEntity() }

            moviesDao.insertAll(movieEntity.orEmpty())

            MediatorResult.Success(endOfPaginationReached = response.body()?.movieData?.isEmpty() == true)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {

        val remoteKey = remoteKeyDao.getKeyByMovie(STATIC_REMOTE_KEY_ID)
            ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private fun setRemoteKey(nextPage: Int?) = RemoteKeyEntity(
        id = STATIC_REMOTE_KEY_ID, nextPage = nextPage, lastUpdated = System.currentTimeMillis()
    )
}
