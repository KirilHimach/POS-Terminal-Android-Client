package by.terminal.posterminalandroidklient.domain.repositories

import by.terminal.posterminalandroidklient.data.remote.api.HttpResult
import by.terminal.posterminalandroidklient.data.remote.dto.response.PaymentStatusDto
import by.terminal.posterminalandroidklient.domain.models.Order

internal interface RemoteRepository {
    suspend fun conductOrder(order: Order): HttpResult<PaymentStatusDto>
}