package com.paymob.core.error

import androidx.annotation.Keep


open class AppErrorException(message: String? = null, cause: Throwable? = null) :
    RuntimeException(message, cause)


@Keep
class GeneralException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class IoException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class ResponseError(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)


@Keep
class FailedToDecodeJwt(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class RefreshTokenNotFound(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)


@Keep
class OperationMessage(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class ResponseErrorException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class ResponseUnAuthorizedException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)

@Keep
class ResponseNotFoundErrorException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)


@Keep
class MoviesListException(message: String? = null, cause: Throwable? = null) :
    AppErrorException(message, cause)




