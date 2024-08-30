package com.paymob.core.base_application

import android.app.Application
import com.paymob.core.BuildConfig
import com.paymob.core.crash_reporting.CrashReportingKey
import com.paymob.core.crash_reporting.CrashReportingManager
import com.paymob.core.local_helper.LocaleHelper
import com.paymob.core.storage_manager.SharedPreferencesManager
import com.paymob.core.storage_manager.StorageManager
import com.paymob.core.storage_manager.di.StorageManagerModule
import com.pluto.Pluto
import com.pluto.plugins.network.PlutoNetworkPlugin
import timber.log.Timber
import zerobranch.androidremotedebugger.AndroidRemoteDebugger
import javax.inject.Inject
import javax.inject.Named

abstract class BaseApplication : Application() {

    @Inject
    @Named(StorageManagerModule.ENCRYPTED_SHARED_PREFERENCE)
    lateinit var encryptedSharedPreferenceManager: StorageManager

    @Inject
    lateinit var crashReportingManager: CrashReportingManager

    override fun onCreate() {
        super.onCreate()
        LocaleHelper(SharedPreferencesManager(this)).init(this)
        initCrashReportingManager()
        plantTimberTrees()
        initRemoteDebugger()
        initPluto()
    }

    private fun initCrashReportingManager() {
        if (this::crashReportingManager.isInitialized) addCrashReportingTools(crashReportingManager)
    }

    open fun addCrashReportingTools(crashReportingManager: CrashReportingManager?) = Unit

    private fun plantTimberTrees() {
        Timber.plant(CrashReportingTree())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.plant(RemoteDebuggerTree())
        }
    }

    private inner class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            setPreLogKeys()
            crashReportingManager.log(priority, tag, message, t)
        }
    }

    private fun setPreLogKeys() {
        getPreLogKeys().forEach { (key, value) ->
            crashReportingManager.setCrashReportingKey(
                key, value
            )
        }
    }

    open fun getPreLogKeys(): List<Pair<CrashReportingKey, String?>> = listOf()

    private fun initRemoteDebugger() {
        if (!BuildConfig.DEBUG) return
        val remoteDebugger =
            AndroidRemoteDebugger.Builder(applicationContext).disableInternalLogging()
                .port(getRemoteDebuggerPort()).build()
        AndroidRemoteDebugger.init(remoteDebugger)
    }

    abstract fun getRemoteDebuggerPort(): Int

    private fun initPluto() = Pluto.Installer(this).apply {
        getPlutoPlugins().forEach { addPlugin(it) }
    }.install()


    open fun getPlutoPlugins() = listOf(PlutoNetworkPlugin("network"))

    private inner class RemoteDebuggerTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            AndroidRemoteDebugger.Log.log(priority, tag, message, t)
        }
    }

}