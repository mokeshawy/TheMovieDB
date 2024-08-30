package com.paymob.core.local_helper

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.paymob.core.storage_manager.StorageManager
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject


private const val SELECTED_LANGUAGE_STORAGE_KEY = "Selected language"

class LocaleHelper @Inject constructor(private val storageManager: StorageManager) {


    fun init(@ApplicationContext application: Application) {
        Lingver.init(application).setLocale(application, getLocale())
    }

    fun setLocale(context: Activity, language: SupportedLanguage) {
        persist(language)
        Lingver.getInstance().setLocale(context, language.locale)
    }

    fun getLocale(): Locale {
        val selectedLocale = storageManager.getString(SELECTED_LANGUAGE_STORAGE_KEY)
        if (selectedLocale != null) {
            return Locale(selectedLocale)
        }
        val deviceLange = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        return SupportedLanguage.entries
            .find { it.locale.language.equals(deviceLange?.language, true) }?.locale
            ?: SupportedLanguage.ENGLISH.locale
    }

    fun isEnglishSelected() = getLocale() == SupportedLanguage.ENGLISH.locale

    private fun persist(language: SupportedLanguage) {
        storageManager.setString(SELECTED_LANGUAGE_STORAGE_KEY, language.locale.language)
    }

    fun <T> getLocalizedValue(enValue: T, localeValue: T) = when {
        isEnglishSelected() -> enValue
        else -> localeValue
    }

    enum class SupportedLanguage(val locale: Locale) { ENGLISH(Locale("en")), ARABIC(Locale("ar")) }
}