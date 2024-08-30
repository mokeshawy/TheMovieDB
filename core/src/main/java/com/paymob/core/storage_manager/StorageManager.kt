package com.paymob.core.storage_manager

interface StorageManager {


    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun setInteger(key: String, value: Int)
    fun getInteger(key: String, defaultValue: Int): Int

    fun setFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float): Float

    fun setString(key: String, value: String)
    fun getString(key: String, defaultValue: String? = null): String?

    fun <T> setObject(key: String, objects: T)
    fun <T> getObject(key: String, type: Class<T>): T?

    fun getAll(): Map<String, Any?>?

    fun remove(key: String)
}