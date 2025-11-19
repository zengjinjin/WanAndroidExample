package com.zoe.wan.android.example.repository.data

/**
 * @Description:
 * @Author: zengjinjin
 * @CreateDate: 2025/11/13
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

object NetworkHelper {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
