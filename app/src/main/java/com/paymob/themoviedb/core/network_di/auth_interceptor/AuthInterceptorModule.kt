package com.paymob.themoviedb.core.network_di.auth_interceptor

import com.paymob.themoviedb.core.utils.Constants.TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AuthInterceptorModule {

    @Provides
    @Named("AuthInterceptor")
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val newBuilder = chain.request().newBuilder()
        newBuilder.addHeader("Authorization", "Bearer $TOKEN")
        newBuilder.build().let { chain.proceed(it) }
    }
}