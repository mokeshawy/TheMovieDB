package com.paymob.core.crash_reporting.crash_reporting_key

import com.paymob.core.crash_reporting.CrashReportingKey

class AppCrashReportingKeys {

    enum class AppErrorKeys(override val key: String) : CrashReportingKey {
        TOKEN("TOKEN")
    }
}