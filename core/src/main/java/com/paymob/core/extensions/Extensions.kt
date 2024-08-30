package com.paymob.core.extensions

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import java.security.MessageDigest
import java.util.Locale

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.localize(@StringRes stringRes: Int) = getString(stringRes)

fun Context.localize(@StringRes stringRes: Int, vararg args: Any) = getString(stringRes, *args)


fun View.setBackGroundResource(@DrawableRes resource: Int) =
    this.setBackgroundResource(resource)

fun TextView.setTextViewColor(context: Context, @ColorRes color: Int) =
    this.setTextColor(AppCompatResources.getColorStateList(context, color))


fun ImageView.setIvTintColor(context: Context, @ColorRes color: Int) {
    this.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}


fun EditText.removeFirstZeroFromPhoneNumber() {
    this.doAfterTextChanged {
        if (!it.isNullOrEmpty() && it.first() == '0') {
            it.clear()
        }
    }
}

fun String.containsArabicLetters(): Boolean {
    indices.forEach {
        val c = codePointAt(it)
        if (c in 0x0600..0x06E0) return true
        Character.charCount(c)
    }
    return false
}

fun String.getLowerCase() = this.lowercase(Locale.getDefault())

fun String.getUberCase() = this.uppercase(Locale.getDefault())


fun View.setOnGoToConnectionSettingClicked(context: Context) = setOnClickListener {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    } else {
        context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}

fun String.isValidPhoneNumber(countryCode: String = "0"): Boolean {
    val regexEg = Regex("^(010|011|012|015)[0-9]{8}$")
    val regexIraq = Regex("^(079|078|077|076|075|074|073)[0-9]{8}$")
    val regexSau = Regex("^(050|053|054|055|056|057|058|059)\\d{7}")
    var isValidNumber = false
    when (countryCode) {
        "+20" -> isValidNumber = regexEg.matches(this)
        "+964" -> isValidNumber = regexIraq.matches(this)
        "+966" -> isValidNumber = regexSau.matches(this)
        "0" -> isValidNumber =
            regexEg.matches(this) || regexIraq.matches(this) || regexSau.matches(this)
    }
    return isValidNumber
}


fun String.isValidKsaPhoneNumber(): Boolean {
    val regex = Regex("""^((\+966|00966|0)(5\d{8}))$""")
    return regex.matches(this)
}


fun String.isValidInternationalPhoneNumber(): Boolean {
    val pattern = "^\\+?[1-9]\\d{1,2}[ -]?\\(?\\d{1,4}\\)?[ -]?\\d{1,4}[ -]?\\d{1,4}[ -]?\\d{1,9}\$"
    val regex = Regex(pattern)
    return regex.matches(this)
}


fun Context.sendToWhatsapp(phoneNumber: String) {
    try {
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    } catch (e: Exception) {
        showToast("Whatsapp not installed!")
    }
}

fun Context.formatNumber(@StringRes res: Int, value: Any?) =
    String.format(Locale.ENGLISH, getString(res), value)


fun String.hashStringToSha256() =
    MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        .fold("") { string, it -> "${string}%02x".format(it) }


fun String.toDigitsOnly() = this.replace("[^0-9]".toRegex(), "")


fun View.handleViewVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun String.isInvalidEmail(): Boolean {
    return !Regex("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$").matches(this)
}