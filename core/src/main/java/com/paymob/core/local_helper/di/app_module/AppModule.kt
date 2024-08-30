package com.paymob.core.local_helper.di.app_module

import com.paymob.core.local_helper.LocaleHelper
import com.paymob.core.storage_manager.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalHelper(sharedPreferencesManager: SharedPreferencesManager) =
        LocaleHelper(sharedPreferencesManager)

}