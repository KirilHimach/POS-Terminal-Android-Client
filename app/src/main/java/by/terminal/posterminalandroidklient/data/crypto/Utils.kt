package by.terminal.posterminalandroidklient.data.crypto

internal fun toLengthBytes(length: Int): List<Byte> {
    if (length > 0xFFFF) throw IllegalArgumentException("Length exceeds 2 bytes")
    return listOf(
        (length shr 8).toByte(),
        length.toByte()
    )
}