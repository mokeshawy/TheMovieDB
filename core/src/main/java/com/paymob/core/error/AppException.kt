package com.paymob.core.error

data class AppException(val appError: AppError) : Exception()