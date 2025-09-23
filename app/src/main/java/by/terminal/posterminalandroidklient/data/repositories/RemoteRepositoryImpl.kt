package by.terminal.posterminalandroidklient.data.repositories

import by.terminal.posterminalandroidklient.data.crypto.KeyManager
import by.terminal.posterminalandroidklient.data.crypto.PacketBuilder
import by.terminal.posterminalandroidklient.data.crypto.TLVEncoder
import by.terminal.posterminalandroidklient.data.remote.api.HttpResult
import by.terminal.posterminalandroidklient.data.remote.dto.response.PaymentStatusDto
import by.terminal.posterminalandroidklient.data.repositories.remote.TransactionRepo
import by.terminal.posterminalandroidklient.di.base.safeApiCall
import by.terminal.posterminalandroidklient.domain.models.Order
import by.terminal.posterminalandroidklient.domain.repositories.RemoteRepository
import javax.inject.Inject

internal class RemoteRepositoryImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : RemoteRepository {
    override suspend fun conductOrder(order: Order): HttpResult<PaymentStatusDto> {
        val rsaPublicKey = KeyManager.loadPublicKey(TestData.RSA_PUBLIC_KEY)
        val hmac = loadHmac()
        val packet = PacketBuilder()
            .setRsa(rsaPublicKey)
            .setHmac(hmac)
            .setTlvData(tlv = TLVEncoder().encode(obj = order))
            .build()
        return safeApiCall(retries = 2) { transactionRepo.conductOrder(transaction = packet) }
    }

    private suspend fun loadHmac(): String {
        val hmac = TestData.HMAC_KEY
        if (hmac.count == 10) {
            val freshHmacResponse = safeApiCall { transactionRepo.fetchFreshHmacKey() }
            when (freshHmacResponse) {
                is HttpResult.Success -> {
                    TestData.HMAC_KEY = freshHmacResponse.body
                }
                is HttpResult.Failure -> {}
                is HttpResult.Error -> {}
            }
            return TestData.HMAC_KEY.hmacKey
        } else {
            TestData.HMAC_KEY.count++
            return hmac.hmacKey
        }
    }
}