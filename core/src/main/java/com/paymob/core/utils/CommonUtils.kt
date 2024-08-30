package com.paymob.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.paymob.core.R
import com.paymob.core.error.AppError
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

object CommonUtils {

    // operation read Android ID from device.
    @Suppress("HardwareIds")
    fun getAndroidId(context: Context): String {
        //TODO Save android id in Shared Preference first if null get it from device
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDeviceInformation(): String {
        return """
            VERSION.RELEASE : ${Build.VERSION.RELEASE}
            VERSION.INCREMENTAL : ${Build.VERSION.INCREMENTAL}
            VERSION.SDK.NUMBER : ${Build.VERSION.SDK_INT}
            BOARD : ${Build.BOARD}
            BOOTLOADER : ${Build.BOOTLOADER}
            BRAND : ${Build.BRAND}
            DISPLAY : ${Build.DISPLAY}
            FINGERPRINT : ${Build.FINGERPRINT}
            HARDWARE : ${Build.HARDWARE}
            HOST : ${Build.HOST}
            ID : ${Build.ID}
            MANUFACTURER : ${Build.MANUFACTURER}
            MODEL : ${Build.MODEL}
            PRODUCT : ${Build.PRODUCT}
            TAGS : ${Build.TAGS}
            TYPE : ${Build.TYPE}
            """.trimIndent()
    }


    fun setLayoutParams(dialog: Dialog) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    fun ImageView.load(context: Context, image: Any) {
        this.load(image) {
            crossfade(true)
            placeholder(circularProgressDrawable(context))
        }
    }

    private fun circularProgressDrawable(context: Context) =
        CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 100f
            setColorSchemeColors(ContextCompat.getColor(context, R.color.blue))
            start()
        }

    fun <T> getLocalizedValue(enValue: T, localeValue: T) = when ((Locale.getDefault().language)) {
        "en" -> enValue
        else -> localeValue
    }


    fun bitmapFromVector(activity: Activity, @DrawableRes vectorResId: Int): BitmapDescriptor {
        val vector = ContextCompat.getDrawable(activity, vectorResId)
        vector!!.setBounds(0, 0, vector.intrinsicWidth, vector.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(
            vector.intrinsicWidth, vector.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vector.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    fun startApplicationSetting(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    fun openTheLocationSetting(activity: Activity) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivity(intent)
    }


    fun getCurrentTime() = getSimpleDateFormat("HH:mm")


    fun getCurrentDate() = getSimpleDateFormat("yyyy-MM-dd")


    @SuppressLint("SimpleDateFormat")
    private fun getSimpleDateFormat(pattern: String): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        return dateFormat.format(currentDate)
    }


    @SuppressLint("SimpleDateFormat")
    fun decodeDateString(dateString: String, pattern: String): String? {
        val inputDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        val pressDate = inputDateFormat.parse(dateString)
        return inputDateFormat.format(pressDate ?: "")
    }


    fun getDatePlusTwentyFourHour(pattern: String): String {
        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.HOUR_OF_DAY, 24)
        val updatedDate = currentTime.time
        val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        return dateFormat.format(updatedDate)
    }


    fun openFileWithActionView(activity: Activity, fileUri: Uri) {
        val extension = fileUri.toString().substringAfterLast('.', "")
        val mimeType = getMimeTypeFromExtension(extension)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, mimeType)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            AppError.E(exception = e, "Open file with action view error")
        }
    }

    private fun getMimeTypeFromExtension(extension: String): String {
        return when (extension.lowercase(Locale.getDefault())) {
            "jpg", "jpeg", "png" -> "image/*"
            "pdf" -> "application/pdf"
            "docx", "doc" -> "application/*"
            else -> "*/*"
        }
    }

    fun handleTimerCallBack(delay: Long, period: Long, action: () -> Unit) {
        val timer = Timer()
        val scrollTask = object : TimerTask() {
            override fun run() {
                action.invoke()
            }
        }
        timer.schedule(scrollTask, delay, period)
    }

    fun sendPhoneNumberToDial(context: Context, mobileNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: $mobileNumber")
        context.startActivity(intent)
    }
}