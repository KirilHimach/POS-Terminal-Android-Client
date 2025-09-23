package by.terminal.posterminalandroidklient.domain.use_cases

import by.terminal.posterminalandroidklient.data.remote.api.mapToRemoteResult
import by.terminal.posterminalandroidklient.data.remote.dto.response.toPaymentStatus
import by.terminal.posterminalandroidklient.data.repositories.TestData
import by.terminal.posterminalandroidklient.domain.models.Order
import by.terminal.posterminalandroidklient.domain.models.PaymentStatus
import by.terminal.posterminalandroidklient.domain.repositories.RemoteRepository
import by.terminal.posterminalandroidklient.domain.repositories.RemoteResult
import javax.inject.Inject

internal class TransactionUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    internal suspend fun buildOrderAndSend(
        cardPan: String,
        amount: String
    ): RemoteResult<PaymentStatus> {
        val order = Order.Builder()
            .cardPan(cardPan = cardPan)
            .amountToCent(amount = amount)
            .merchantID(merchantID = TestData.MERCHANT_ID)
            .buildRandomUuidAndTime()
            .build()
        return remoteRepository.conductOrder(order = order).mapToRemoteResult { dto -> dto.toPaymentStatus() }
    }
}