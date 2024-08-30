package com.paymob.core.storage_manager

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageManager {

    private val sharedPref = context.getSharedPreferences(
        SharedPreferenceKey.SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun setBoolean(key: String, value: Boolean) =
        sharedPref.edit().putBoolean(key, value).apply()

    override fun getBoolean(key: String, defaultValue: Boolean) =
        sharedPref.getBoolean(key, defaultValue)

    override fun setInteger(key: String, value: Int) =
        sharedPref.edit().putInt(key, value).apply()

    override fun getInteger(key: String, defaultValue: Int) =
        sharedPref.getInt(key, defaultValue)

    override fun setFloat(key: String, value: Float) =
        sharedPref.edit().putFloat(key, value).apply()

    override fun getFloat(key: String, defaultValue: Float) =
        sharedPref.getFloat(key, defaultValue)

    override fun setString(key: String, value: String) =
        sharedPref.edit().putString(key, value).apply()

    override fun getString(key: String, defaultValue: String?) =
        sharedPref.getString(key, defaultValue)

    override fun <T> setObject(key: String, objects: T) {
        val jsonString = GsonBuilder().create().toJson(objects)
        sharedPref.edit().putString(key, jsonString).apply()
    }

    override fun <T> getObject(key: String, type: Class<T>): T? {
        val value = sharedPref.getString(key, null)
        return GsonBuilder().create().fromJson(value, type)
    }

    override fun getAll(): Map<String, Any?>? {
        return sharedPref.all
    }

    override fun remove(key: String) {
        sharedPref.edit().remove(key).apply()
    }

}