package by.terminal.posterminalandroidklient.domain.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

internal data class Order private constructor(
    val cardPan: String,
    val amount: Long,
    val transactionID: String,
    val merchantID: String
) {
    internal class Builder {
        private var cardPan: String = ""
        private var amountCent: Long = 0L
        private var transactionID: String = ""
        private var merchantID: String = ""

        internal fun cardPan(cardPan: String) = apply {
            this.cardPan = buildString {
                append(cardPan.take(4))
                append("********")
                append(cardPan.takeLast(4))
            }
        }

        internal fun amountToCent(amount: String) = apply {
            this.amountCent = convertAmountToMinimalBanknote(amount = amount)
        }

        private fun convertAmountToMinimalBanknote(amount: String): Long =
            BigDecimal(amount).movePointRight(2).toLong()

        internal fun buildRandomUuidAndTime() = apply {
            this.transactionID = buildString {
                append(UUID.randomUUID().toString())
                append("_")
                append(LocalDateTime.now())
            }
        }

        internal fun merchantID(merchantID: String) = apply {
            this.merchantID = merchantID
        }

        internal fun build(): Order {
            require(cardPan.isNotEmpty()) { "Card PAN is required." }
            require(amountCent > 0) { "Amount have to be positive." }
            require(transactionID.isNotEmpty()) { "Transaction ID is required." }
            require(merchantID.isNotEmpty()) { "Merchant ID is required" }
            return Order(
                cardPan = cardPan,
                amount = amountCent,
                transactionID = transactionID,
                merchantID = merchantID
            )
        }
    }
}