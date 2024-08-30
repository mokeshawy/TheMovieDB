package com.paymob.core.storage_manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.security.GeneralSecurityException
import java.security.KeyStore
import javax.inject.Inject

class EncryptedSharedPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : StorageManager {

    private val sharedPreferences by lazy {
        try {
            getStoredSharedPreferences()
        } catch (e: Exception) {
            createNewSharedPreferenceFile()
        }
    }

    private fun getStoredSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context, "AppKey")
            .setUserAuthenticationRequired(false)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            SharedPreferenceKey.PREFERENCES_ENCRYPTED_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        trySharedPreferences(sharedPreferences)
        return sharedPreferences
    }

    private fun trySharedPreferences(sharedPreferences: SharedPreferences) {
        // The purpose of the code below is to simulate a crash that happens only on the first run
        // This method is here to fix the bug in encrypted shared preferences after app uninstall
        val num = sharedPreferences.getInt("num", 0)
        sharedPreferences.edit().putInt("num", 1).apply()
        if (num == 0) {
            throw GeneralSecurityException("Something went wrong...")
        }
    }

    private fun createNewSharedPreferenceFile(): SharedPreferences? {
        return try {
            val sharedPrefsFile = File(
                "${context.filesDir.parent}/shared_prefs/${SharedPreferenceKey.PREFERENCES_ENCRYPTED_NAME}.xml"
            )
            if (sharedPrefsFile.exists()) sharedPrefsFile.delete()

            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            keyStore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            getStoredSharedPreferences()
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }


    override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences?.getBoolean(key, defaultValue) ?: defaultValue

    override fun setInteger(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    override fun getInteger(key: String, defaultValue: Int) =
        sharedPreferences?.getInt(key, defaultValue) ?: defaultValue

    override fun setFloat(key: String, value: Float) {
        sharedPreferences?.edit()?.putFloat(key, value)?.apply()
    }

    override fun getFloat(key: String, defaultValue: Float) =
        sharedPreferences?.getFloat(key, defaultValue) ?: defaultValue

    override fun setString(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    override fun getString(key: String, defaultValue: String?): String? =
        sharedPreferences?.getString(key, defaultValue) ?: defaultValue

    override fun <T> setObject(key: String, objects: T) {
        val jsonString = GsonBuilder().create().toJson(objects)
        sharedPreferences?.edit()?.putString(key, jsonString)?.apply()
    }

    override fun <T> getObject(key: String, type: Class<T>): T? {
        val value = sharedPreferences?.getString(key, null)
        return GsonBuilder().create().fromJson(value, type)
    }

    override fun getAll(): Map<String, Any?>? = sharedPreferences?.all

    override fun remove(key: String) {
        sharedPreferences?.edit()?.remove(key)?.apply()
    }
}