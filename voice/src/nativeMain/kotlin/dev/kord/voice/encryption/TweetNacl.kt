package dev.kord.voice.encryption

internal actual object SecretBox {
    actual val boxzerobytesLength: Int get() = TODO()
    actual val zerobytesLength: Int get() = TODO()
    actual val nonceLength: Int get() = TODO()
}

internal actual object Box {
    actual val zerobytesLength: Int get() = TODO()
}

internal actual fun crypto_secretbox(c: ByteArray, m: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int = TODO()
internal actual fun crypto_secretbox_open(m: ByteArray, c: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int = TODO()
