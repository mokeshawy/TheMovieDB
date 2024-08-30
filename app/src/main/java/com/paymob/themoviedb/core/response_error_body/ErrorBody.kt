package com.paymob.themoviedb.core.response_error_body

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response


data class ErrorBody(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean
)

fun Response<*>.getErrorBody(): ErrorBody? =
    Gson().fromJson(errorBody()?.string(), ErrorBody::class.java)