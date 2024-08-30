package com.paymob.themoviedb.core.api_services.di

import com.paymob.themoviedb.core.api_services.MovieDbApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {


    @Singleton
    @Provides
    fun provideMovieDbApiServices(retrofit: Retrofit): MovieDbApiServices =
        retrofit.create(MovieDbApiServices::class.java)
}