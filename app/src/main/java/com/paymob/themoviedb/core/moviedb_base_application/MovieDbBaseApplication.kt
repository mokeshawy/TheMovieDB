package com.paymob.themoviedb.core.moviedb_base_application

import com.google.firebase.FirebaseApp
import com.paymob.core.base_application.BaseApplication
import com.paymob.core.crash_reporting.CrashReportingManager
import com.paymob.core.crash_reporting.crash_reporting_key.AppCrashReportingKeys
import com.paymob.core.crash_reporting.crash_reporting_tools.FirebaseCrashReportingTool
import com.paymob.themoviedb.core.utils.Constants
import javax.inject.Inject

class MovieDbBaseApplication : BaseApplication(){

    @Inject
    lateinit var firebaseCrashReportingTool: FirebaseCrashReportingTool

    override fun onCreate() {
        super.onCreate()
        initFirebaseApp()
    }



    private fun initFirebaseApp() {
        FirebaseApp.initializeApp(this)
    }

    override fun addCrashReportingTools(crashReportingManager: CrashReportingManager?) {
        if (this::firebaseCrashReportingTool.isLateinit) return
        crashReportingManager?.registerCrashReportingTool(firebaseCrashReportingTool)
    }

    override fun getPreLogKeys() =
        listOf(AppCrashReportingKeys.AppErrorKeys.TOKEN to Constants.token)

    override fun getRemoteDebuggerPort() = 4040
}