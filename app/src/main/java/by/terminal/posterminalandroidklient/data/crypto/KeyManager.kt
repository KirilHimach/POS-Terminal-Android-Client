package by.terminal.posterminalandroidklient.data.crypto

import by.terminal.posterminalandroidklient.data.crypto.CryptoConstants.RSA_KEY_ALGORITHM
import org.bouncycastle.util.io.pem.PemReader
import java.io.StringReader
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

internal object KeyManager {
    internal fun loadPublicKey(rsaKey: String): PublicKey {
        val pemReader = PemReader(StringReader(rsaKey))
        val pemObject = pemReader.readPemObject()
        pemReader.close()
        val keyData = pemObject.content
        val keySpec = X509EncodedKeySpec(keyData)
        val keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM)
        return keyFactory.generatePublic(keySpec)
    }
}