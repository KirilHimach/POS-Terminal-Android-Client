package by.terminal.posterminalandroidklient.data.crypto

import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.RSA_KEY_ALGORITHM
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

internal object KeyManager {
    internal fun loadPublicKey(rsaKey: String): PublicKey {
        val rsa = clearPemContent(rsaKey)
        val rsaBytes = Base64.getDecoder().decode(rsa)
        val keySpec = X509EncodedKeySpec(rsaBytes)
        val keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM)
        return keyFactory.generatePublic(keySpec)
    }

    private fun clearPemContent(content: String) = content
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replace("\\s+".toRegex(), "")
}