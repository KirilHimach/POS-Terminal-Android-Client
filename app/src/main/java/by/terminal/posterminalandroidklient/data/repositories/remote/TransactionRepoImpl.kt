package by.terminal.posterminalandroidklient.data.repositories.remote

import by.terminal.posterminalandroidklient.data.remote.api.TransactionApi
import by.terminal.posterminalandroidklient.data.remote.dto.response.FreshHMACDto
import by.terminal.posterminalandroidklient.data.remote.dto.response.PaymentStatusDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

internal class TransactionRepoImpl @Inject constructor(
    private val transactionApi: TransactionApi
) : TransactionRepo {
    override suspend fun conductOrder(transaction: ByteArray): Response<PaymentStatusDto> =
        transactionApi.makePayment(
            transaction = transaction.toRequestBody("application/octet-stream".toMediaType())
        )

    override suspend fun fetchFreshHmacKey(): Response<FreshHMACDto> =
        transactionApi.getHmacKey()
}