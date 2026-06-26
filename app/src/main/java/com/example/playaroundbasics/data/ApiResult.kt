package com.example.playaroundbasics.data

internal sealed class ApiResult<out T> {
    data object Progress : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failed(val message: String?, val throwable: Throwable? = null) : ApiResult<Nothing>()
}
