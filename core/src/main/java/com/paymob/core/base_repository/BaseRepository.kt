package com.paymob.core.base_repository


import com.paymob.core.error.AppError
import com.paymob.core.error.GeneralException
import com.paymob.core.error.IoException
import com.paymob.core.error.ResponseErrorException
import com.paymob.core.error.ResponseUnAuthorizedErrorException
import com.paymob.core.state.State
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.CancellationException


abstract class BaseRepository<RequestDto, ResponseDto> {

    suspend fun getOperationState(requestDto: RequestDto): State<ResponseDto> {
        return try {
            performApiCall(requestDto)
        } catch (e: IOException) {
            State.Error(getIoExceptionError(e))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            State.Error(getGeneralExceptionError(e))
        }
    }

    abstract suspend fun performApiCall(requestDto: RequestDto): State<ResponseDto>

    fun <T> getNotSuccessfulResponseState(response: Response<*>): State<T> {
        return when {
            response.code() == 401 -> State.Error(getUnauthorizedError(response))
            else -> State.Error(getNotSuccessfulResponseError(response))
        }
    }

    private fun getIoExceptionError(e: IOException) = AppError.E(
        exception = IoException(cause = e),
        logMessage = "Failed to load data from Api with IOException:",
    )

    private fun getGeneralExceptionError(e: Exception) = AppError.E(
        exception = GeneralException(cause = e),
        logMessage = "Failed to load data from Api with General exception",
    )

    private fun getNotSuccessfulResponseError(response: Response<*>) = AppError.E(
        exception = ResponseErrorException(),
        logMessage = "Api request to url: ${response.raw().request.url}: failed with code ${response.code()}",
        extraData = response
    )

    private fun getUnauthorizedError(response: Response<*>) = AppError.E(
        exception = ResponseUnAuthorizedErrorException(),
        logMessage = "Api request to url: ${response.raw().request.url}: failed with code ${response.code()}",
        extraData = response
    )
}