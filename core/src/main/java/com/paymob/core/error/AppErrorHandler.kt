package com.paymob.core.error

interface AppErrorHandler {
    fun handleError(error: AppError, callback: AppError.() -> Unit = {})
}