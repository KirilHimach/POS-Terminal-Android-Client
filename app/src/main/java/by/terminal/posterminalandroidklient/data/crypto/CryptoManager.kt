package by.terminal.posterminalandroidklient.data.crypto

import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.AES_GSM_ALGORITHM
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.AES_KEY_ALGORITHM
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.AES_KEY_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.DIGEST_ALGORITHM
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.GCM_TAG_LENGTH
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.HMAC_SHA256_ALGORITHM
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.IV_SIZE
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.MASK_GENERATION_FUNCTION
import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.RSA_OAEP_ALGORITHM
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.MGF1ParameterSpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource
import javax.crypto.spec.SecretKeySpec

internal object CryptoManager {
    internal fun generateSessionKey() = ByteArray(AES_KEY_SIZE).apply {
        SecureRandom().nextBytes(this)
    }

    internal fun generateIV() = ByteArray(IV_SIZE).apply {
        SecureRandom().nextBytes(this)
    }

    internal fun encryptSessionKey(sessionKey: ByteArray, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance(RSA_OAEP_ALGORITHM)
        val oaepParams = OAEPParameterSpec(
            DIGEST_ALGORITHM,
            MASK_GENERATION_FUNCTION,
            MGF1ParameterSpec.SHA256,
            PSource.PSpecified.DEFAULT
        )
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams)
        return cipher.doFinal(sessionKey)
    }

    internal fun encryptTlv(data: ByteArray, sessionKey: ByteArray, iv: ByteArray): ByteArray {
        if (iv.size != IV_SIZE) throw IllegalArgumentException("IV must be 12 bytes")
        val cipher = Cipher.getInstance(AES_GSM_ALGORITHM)
        val keySpec = SecretKeySpec(sessionKey, AES_KEY_ALGORITHM)
        val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)
        return cipher.doFinal(data)
    }

    internal fun computeHMAC(data: ByteArray, hmacKey: String): ByteArray {
        val keyBytes = Base64.getDecoder().decode(hmacKey)
        val mac = Mac.getInstance(HMAC_SHA256_ALGORITHM)
        val keySpec = SecretKeySpec(keyBytes, HMAC_SHA256_ALGORITHM)
        mac.init(keySpec)
        return mac.doFinal(data)
    }
}