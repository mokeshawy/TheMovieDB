package com.paymob.core.connectivity.internet_access_observer

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException


const val READ_TIME_OUT = 500
const val CONNECT_TIME_OUT = 5000
const val REQUEST_METHOD = "GET"
const val PING_URL = "www.google.com"

class InternetAccessObserver @Inject constructor(private val activity: Activity) {

    private val internetAccessErrorHandler = (activity as? InternetAccessErrorHandler)

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    fun getInternetAccessResponse() {
        (activity as AppCompatActivity).lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                _isInternetAvailable.postValue(isInternetAccess())
            }
        }
    }

    private fun isInternetAccess(): Boolean {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return InetAddress.getByName(PING_URL).isReachable(CONNECT_TIME_OUT)
            }
            val httpConnection = URL("https://$PING_URL").openConnection() as HttpURLConnection
            httpConnection.apply {
                readTimeout = READ_TIME_OUT
                connectTimeout = CONNECT_TIME_OUT
                requestMethod = REQUEST_METHOD
                connect()
                return responseCode == 200
            }
        } catch (e: SocketTimeoutException) {
            getInternetExceptionError(SOCKET_TIME_OUT_EXCEPTION, e)
        } catch (e: SSLHandshakeException) {
            getInternetExceptionError(SSL_HANDSHAKE_EXCEPTION, e)
        } catch (e: UnknownHostException) {
            getInternetExceptionError(UNKNOWN_HOST_EXCEPTION, e)
        } catch (e: Exception) {
            getInternetExceptionError(GENERAL_EXCEPTION, e)
        }
        return false
    }

    private fun getInternetExceptionError(errorType: String, exception: Exception) =
        internetAccessErrorHandler?.readInternetAccessExceptionError(errorType, exception)


    companion object {
        const val SOCKET_TIME_OUT_EXCEPTION = "SOCKET_TIME_OUT_EXCEPTION"
        const val SSL_HANDSHAKE_EXCEPTION = "SSL_HANDSHAKE_EXCEPTION"
        const val UNKNOWN_HOST_EXCEPTION = "UNKNOWN_HOST_EXCEPTION"
        const val GENERAL_EXCEPTION = "GENERAL_EXCEPTION"
    }
}