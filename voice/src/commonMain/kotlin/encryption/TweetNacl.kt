package dev.kord.voice.encryption


internal expect object SecretBox {
    val boxzerobytesLength: Int
    val zerobytesLength: Int
    val nonceLength: Int
}

internal expect object Box {
    val zerobytesLength: Int
}

internal expect fun crypto_secretbox(c: ByteArray, m: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int
internal expect fun crypto_secretbox_open(m: ByteArray, c: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int
