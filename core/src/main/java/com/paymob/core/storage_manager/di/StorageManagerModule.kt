package com.paymob.core.storage_manager.di

import android.content.Context
import com.paymob.core.storage_manager.EncryptedSharedPreferenceManager
import com.paymob.core.storage_manager.SharedPreferencesManager
import com.paymob.core.storage_manager.StorageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageManagerModule {

    const val ENCRYPTED_SHARED_PREFERENCE = "EncryptedSharedPreference"
    const val SHARED_PREFERENCE = "SharedPreference"

    @Singleton
    @Named(ENCRYPTED_SHARED_PREFERENCE)
    @Provides
    fun provideEncryptedSharedPreference(@ApplicationContext context: Context): StorageManager =
        EncryptedSharedPreferenceManager(context)

    @Singleton
    @Named(SHARED_PREFERENCE)
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): StorageManager =
        SharedPreferencesManager(context)

}