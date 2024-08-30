package com.paymob.core.crash_reporting.di

import com.paymob.core.crash_reporting.CrashReportingManager
import com.paymob.core.crash_reporting.AppCrashReportingManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CrashReportingModule {

    @Binds
    @Singleton
    abstract fun provideCrashReportingManager(reportingManager: AppCrashReportingManager): CrashReportingManager
}
