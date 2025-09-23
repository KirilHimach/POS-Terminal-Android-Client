package by.terminal.posterminalandroidklient.data.repositories.remote

import by.terminal.posterminalandroidklient.data.remote.dto.response.FreshHMACDto
import by.terminal.posterminalandroidklient.data.remote.dto.response.PaymentStatusDto
import retrofit2.Response

internal interface TransactionRepo {
    suspend fun conductOrder(transaction: ByteArray): Response<PaymentStatusDto>
    suspend fun fetchFreshHmacKey(): Response<FreshHMACDto>
}