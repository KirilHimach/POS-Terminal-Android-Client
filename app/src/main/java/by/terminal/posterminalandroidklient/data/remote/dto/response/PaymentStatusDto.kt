package by.terminal.posterminalandroidklient.data.remote.dto.response

import by.terminal.posterminalandroidklient.domain.models.PaymentStatus
import com.google.gson.annotations.SerializedName

internal data class PaymentStatusDto(
    @SerializedName("status") val status: String?,
    @SerializedName("authCode") val authCode: Int?,
    @SerializedName("timestamp") val timestamp: String?
)

internal fun PaymentStatusDto.toPaymentStatus() = PaymentStatus(
    status = status ?: "",
    authCode = authCode ?: 0,
    timestamp = timestamp ?: ""
)