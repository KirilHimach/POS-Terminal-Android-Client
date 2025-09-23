package by.terminal.posterminalandroidklient.domain.models

internal data class PaymentStatus(
    val status: String,
    val authCode: Int,
    val timestamp: String
)