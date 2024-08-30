package com.paymob.core.crash_reporting

import com.paymob.core.crash_reporting.crash_reporting_tools.AppReportingTool

interface CrashReportingManager {

    val appReportingTools: Map<CrashReportingToolIdentifier, AppReportingTool>
    val crashReportingKeys: Map<String, Any?>

    fun log(priority: Int, tag: String?, message: String, t: Throwable?)

    fun registerCrashReportingTool(appReportingTool: AppReportingTool)

    fun unRegisterCrashReportingTool(identifier: CrashReportingToolIdentifier)

    fun getCrashReportingTool(identifier: CrashReportingToolIdentifier): AppReportingTool?

    fun initCrashReportingKeys()

    fun setCrashReportingKey(key: String, value: Any?, stored: Boolean = false)

    fun setCrashReportingKey(key: CrashReportingKey, value: Any?, stored: Boolean = false)

}

