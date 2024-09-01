package com.paymob.themoviedb.feature.fragments.comoun.data.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paymob.themoviedb.feature.fragments.comoun.data.model.MovieEntity
import com.paymob.themoviedb.feature.fragments.comoun.data.model.RemoteKeyEntity

@Database(entities = [MovieEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun remoteKeyDo(): RemoteKeyDao
}