package com.paymob.core.state

import com.paymob.core.error.AppError


sealed class State<T> {
    var hasBeenHandled = false

    class Initial<T> : State<T>()

    class Loading<T> : State<T>()
    data class Success<T>(val data: T? = null, val error: AppError? = null) : State<T>()
    data class Error<T>(val error: AppError) : State<T>()

    companion object {
        fun <T> infoError(exception: Exception) = Error<T>(AppError.I(exception = exception))
        fun <T> warnError(exception: Exception, message: String? = null) =
            Error<T>(AppError.W(exception = exception, logMessage = message))

        fun <T> criticalError(exception: Exception) = Error<T>(AppError.E(exception = exception))
    }
}