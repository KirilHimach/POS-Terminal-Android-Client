package by.terminal.posterminalandroidklient.data.remote.api

import by.terminal.posterminalandroidklient.domain.repositories.RemoteResult

internal sealed class HttpResult<out T> {
    data class Success<out T>(val body: T) : HttpResult<T>()
    data class Failure(val e: String) : HttpResult<Nothing>()
    data class Error(val e: Throwable) : HttpResult<Nothing>()
}

internal inline fun <T, R> HttpResult<T>.mapToRemoteResult(transform: (T) -> R): RemoteResult<R> = when (this) {
    is HttpResult.Success -> RemoteResult.Success(transform(body))
    is HttpResult.Failure -> RemoteResult.Failure(e)
    is HttpResult.Error -> RemoteResult.Error(e)
}