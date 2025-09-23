package by.terminal.posterminalandroidklient.data.crypto

import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.HEADER_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.HMAC_ARRAY_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.IV_ARRAY_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.IV_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.MESSAGE_TYPE_TAG
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.PROTOCOL_VERSION_TAG
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.SESSION_KEY_ARRAY_SIZE
import java.security.PublicKey

internal class PacketBuilder {
    private var rsaPublicKey: PublicKey? = null
    private var hmacKey: String = ""
    private var tlvData: ByteArray = byteArrayOf()

    internal fun setRsa(rsaPublicKey: PublicKey): PacketBuilder = apply { this.rsaPublicKey = rsaPublicKey }

    internal fun setHmac(hmac: String): PacketBuilder = apply { this.hmacKey = hmac }

    internal fun setTlvData(tlv: ByteArray): PacketBuilder = apply { this.tlvData = tlv }

    internal fun build(): ByteArray {
        requireNotNull(rsaPublicKey) { "RSA key is required." }
        require(hmacKey.isNotEmpty()) { "HMAC key is required." }

        val sessionKey = CryptoManager.generateSessionKey()
        val iv = CryptoManager.generateIV()
        val encryptedTlv = CryptoManager.encryptTlv(data = tlvData, sessionKey = sessionKey, iv = iv)
        val hmac = CryptoManager.computeHMAC(data = encryptedTlv, hmacKey = hmacKey)
        val encryptedSessionKey = CryptoManager.encryptSessionKey(sessionKey = sessionKey, publicKey = rsaPublicKey!!)

        validateInput(encryptedSessionKey = encryptedSessionKey, iv = iv, hmac = hmac)
        val header = createHeader(encryptedTlv.size)
        return buildList {
            addAll(header.toList())
            addAll(encryptedSessionKey.toList())
            addAll(iv.toList())
            addAll(hmac.toList())
            addAll(encryptedTlv.toList())
        }.toByteArray()
    }

    private fun validateInput(encryptedSessionKey: ByteArray, iv: ByteArray, hmac: ByteArray) {
        if (encryptedSessionKey.size != SESSION_KEY_ARRAY_SIZE) throw IllegalArgumentException("Session key must be 256 bytes")
        if (iv.size != IV_SIZE) throw IllegalArgumentException("IV must be 12 bytes")
        if (hmac.size != HMAC_ARRAY_SIZE) throw IllegalArgumentException("HMAC must be 32 bytes")
    }

    private fun createHeader(tlvLength: Int): ByteArray {
        val totalLength = HEADER_SIZE + SESSION_KEY_ARRAY_SIZE + IV_ARRAY_SIZE + HMAC_ARRAY_SIZE + tlvLength
        if (totalLength > 0xFFFF) throw IllegalArgumentException("Packet size exceeds 2 bytes")
        return byteArrayOf(
            PROTOCOL_VERSION_TAG,
            MESSAGE_TYPE_TAG,
            (totalLength shr 8).toByte(),
            totalLength.toByte()
        )
    }
}