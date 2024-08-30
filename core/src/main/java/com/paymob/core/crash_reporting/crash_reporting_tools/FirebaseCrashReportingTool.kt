package com.paymob.core.crash_reporting.crash_reporting_tools

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.paymob.core.crash_reporting.CrashReportingToolIdentifier
import javax.inject.Inject

class FirebaseCrashReportingTool @Inject constructor() : AppReportingTool {

    override val identifier = CrashReportingToolIdentifier(this::class.simpleName!!)
    private val crashlytics get() = FirebaseCrashlytics.getInstance()

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        crashlytics.log(message)
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) return
        crashlytics.recordException(throwable ?: RuntimeException("Unhandled Exception"))
    }

    override fun setCrashReportingKey(key: String, value: Any?) {
        crashlytics.setCustomKey(key, value.toString())
    }
}