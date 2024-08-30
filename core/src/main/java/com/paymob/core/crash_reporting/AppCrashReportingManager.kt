package com.paymob.core.crash_reporting

import com.paymob.core.crash_reporting.crash_reporting_tools.AppReportingTool
import com.paymob.core.storage_manager.StorageManager
import com.paymob.core.storage_manager.di.StorageManagerModule
import javax.inject.Inject
import javax.inject.Named


class AppCrashReportingManager @Inject constructor(
    @Named(StorageManagerModule.SHARED_PREFERENCE)
    private val storageManager: StorageManager
) : CrashReportingManager {

    override val appReportingTools =
        mutableMapOf<CrashReportingToolIdentifier, AppReportingTool>()
    override val crashReportingKeys = mutableMapOf<String, Any?>()

    init {
        initCrashReportingKeys()
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        appReportingTools.values.forEach {
            it.log(priority, tag, message, t)
        }
    }


    override fun registerCrashReportingTool(appReportingTool: AppReportingTool) {
        appReportingTools[appReportingTool.identifier] = appReportingTool
    }

    override fun unRegisterCrashReportingTool(identifier: CrashReportingToolIdentifier) {
        appReportingTools.remove(identifier)
    }

    override fun getCrashReportingTool(identifier: CrashReportingToolIdentifier) =
        appReportingTools[identifier]

    override fun initCrashReportingKeys() {
        storageManager.getAll()
            ?.filter { it.key.startsWith(CRASH_REPORT_KEY) }
            ?.map { (k, v) -> k.removePrefix(CRASH_REPORT_KEY) to v.toString() }
            ?.forEach { setCrashReportingKey(it.first, it.second) }
    }


    override fun setCrashReportingKey(key: String, value: Any?, stored: Boolean) {
        crashReportingKeys[key] = value
        appReportingTools.values.forEach { it.setCrashReportingKey(key, value) }
        if (stored) storageManager.setString("$CRASH_REPORT_KEY$key", value.toString())
    }

    override fun setCrashReportingKey(key: CrashReportingKey, value: Any?, stored: Boolean) {
        setCrashReportingKey(key.key, value, stored)
    }

    companion object {
        private const val CRASH_REPORT_KEY = "CRK_"
        private const val APP_VERSION = "APP_VERSION"
    }
}