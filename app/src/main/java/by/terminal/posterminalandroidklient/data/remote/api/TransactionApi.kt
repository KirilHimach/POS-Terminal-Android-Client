package by.terminal.posterminalandroidklient.data.remote.api

import by.terminal.posterminalandroidklient.data.remote.dto.response.FreshHMACDto
import by.terminal.posterminalandroidklient.data.remote.dto.response.PaymentStatusDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface TransactionApi {
    @POST("payment")
    @Headers("Content-Type: application/octet-stream")
    suspend fun makePayment(@Body transaction: RequestBody): Response<PaymentStatusDto>

    @POST("exchangeHmac")
    suspend fun getHmacKey(): Response<FreshHMACDto>
}