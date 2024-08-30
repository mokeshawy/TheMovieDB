package com.paymob.core.crash_reporting.crash_reporting_tools

import com.paymob.core.crash_reporting.CrashReportingToolIdentifier

interface AppReportingTool {
    val identifier: CrashReportingToolIdentifier
    fun log(priority: Int, tag: String?, message: String, throwable: Throwable?)
    fun setCrashReportingKey(key: String, value: Any?)
}