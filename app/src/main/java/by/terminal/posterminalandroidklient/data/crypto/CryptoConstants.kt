package by.terminal.posterminalandroidklient.data.crypto

internal object CryptoConstants {
    internal const val PROTOCOL_VERSION_TAG: Byte = 0x01
    internal const val MESSAGE_TYPE_TAG: Byte = 0X01
    internal const val PAN_TAG: Byte = 0x10
    internal const val AMOUNT_TAG: Byte = 0x20
    internal const val TRANSACTION_ID_TAG: Byte = 0x30
    internal const val MERCHANT_ID_TAG: Byte = 0x40

    internal const val RSA_KEY_ALGORITHM = "RSA"
    internal const val AES_GSM_ALGORITHM = "AES/GCM/NoPadding"
    internal const val AES_KEY_ALGORITHM = "AES"
    internal const val RSA_OAEP_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
    internal const val HMAC_SHA256_ALGORITHM = "HmacSHA256"
    internal const val DIGEST_ALGORITHM = "SHA-256"
    internal const val MASK_GENERATION_FUNCTION = "MGF1"

    internal const val AES_KEY_SIZE = 32
    internal const val IV_SIZE = 12
    internal const val GCM_TAG_LENGTH = 128
    internal const val SESSION_KEY_ARRAY_SIZE = 256
    internal const val HMAC_ARRAY_SIZE = 32
    internal const val HEADER_SIZE = 4
    internal const val IV_ARRAY_SIZE = 12
}