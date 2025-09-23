package by.terminal.posterminalandroidklient.data.crypto

import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.AMOUNT_TAG
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.MERCHANT_ID_TAG
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.PAN_TAG
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.TRANSACTION_ID_TAG
import by.terminal.posterminalandroidklient.domain.models.Order

internal class TLVEncoder {

    internal fun encode(obj: Order): ByteArray {
        val tlvData = mutableListOf<Byte>()
        encodeField(tlvData, PAN_TAG, obj.cardPan.toByteArray(Charsets.UTF_8))
        encodeField(tlvData, AMOUNT_TAG, obj.amount.toString().toByteArray(Charsets.UTF_8))
        encodeField(tlvData, TRANSACTION_ID_TAG, obj.transactionID.toByteArray(Charsets.UTF_8))
        encodeField(tlvData, MERCHANT_ID_TAG, obj.merchantID.toByteArray(Charsets.UTF_8))
        return tlvData.toByteArray()
    }

    private fun encodeField(buffer: MutableList<Byte>, tag: Byte, value: ByteArray) {
        if (value.size > 0xFFFF) throw IllegalArgumentException("Field length exceeds 2 bytes")
        buffer.add(tag)
        buffer.addAll(toLengthBytes(value.size))
        buffer.addAll(value.toList())
    }
}