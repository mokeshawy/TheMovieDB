package com.paymob.themoviedb.feature.fragments.comoun.data.room_database.di

import android.content.Context
import androidx.room.Room
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.MoviesDao
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.MoviesDatabase
import com.paymob.themoviedb.feature.fragments.comoun.data.room_database.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val MOVIES_DATABASE = "MOVIES_DATABASE"

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao =
        moviesDatabase.moviesDao()


    @Provides
    fun provideRemoteKeyDao(moviesDatabase: MoviesDatabase): RemoteKeyDao =
        moviesDatabase.remoteKeyDo()


    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, MOVIES_DATABASE)
            .fallbackToDestructiveMigration().build()

}