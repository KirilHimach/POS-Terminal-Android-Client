package by.terminal.posterminalandroidklient.domain.repositories

internal sealed class RemoteResult<out T> {
    data class Success<out T>(val body: T) : RemoteResult<T>()
    data class Failure(val e: String) : RemoteResult<Nothing>()
    data class Error(val e: Throwable) : RemoteResult<Nothing>()
    data object Default : RemoteResult<Nothing>()
}