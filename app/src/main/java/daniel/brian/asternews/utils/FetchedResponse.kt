package daniel.brian.asternews.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class FetchedResponse<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : FetchedResponse<T>(data = data)
    class Error<T>(message: String?) : FetchedResponse<T>(message = message)
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
): FetchedResponse<T> {
    return withContext(dispatcher) {
        try {
            FetchedResponse.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> FetchedResponse.Error("No Internet connection")
                is HttpException -> FetchedResponse.Error("Server Error")
                else -> FetchedResponse.Error(throwable.message ?: "UnExpected Error")
            }
        }
    }
}
