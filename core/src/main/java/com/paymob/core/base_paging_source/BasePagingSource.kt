package com.paymob.core.base_paging_source

import androidx.paging.PagingSource
import com.paymob.core.error.ResponseUnAuthorizedError
import com.paymob.core.error.AppError
import com.paymob.core.error.AppException
import retrofit2.Response

abstract class BasePagingSource<key : Any, value : Any> : PagingSource<key, value>() {

    fun getNotSuccessfulError(response: Response<*>): LoadResult<key, value>? {
        return when {
            response.body() == 401 -> LoadResult.Error(getUnAuthorizedException(response))
            else -> null
        }
    }

    private fun getUnAuthorizedException(response: Response<*>): AppException {
        val appError = AppError.E(
            exception = ResponseUnAuthorizedError(),
            logMessage = "Api request to url: ${response.raw().request.url}: failed with code ${response.code()}",
            extraData = response
        )
        return AppException(appError)
    }
}