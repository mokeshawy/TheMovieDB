package com.paymob.themoviedb.feature.fragments.comoun.data.room_database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.paymob.themoviedb.feature.fragments.comoun.data.model.MovieEntity


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MovieEntity>)

    @Update
    suspend fun update(items: MovieEntity)

    @Query("SELECT * FROM Movie ORDER BY releaseDate DESC")
    fun getMovieList(): List<MovieEntity>

    @Query("SELECT * FROM Movie WHERE isFavorite = 1 ")
    suspend fun getFavoriteMovie(): List<MovieEntity>

    @Query("DELETE FROM Movie WHERE id = :id")
    suspend fun unFavoriteMovie(id: Int)


    @Query("SELECT * FROM Movie ORDER BY releaseDate DESC")
    fun getPagingMovieList(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM Movie")
    suspend fun clearAll()

}