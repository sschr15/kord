@file:JvmName("TweetNaclJvm")

package dev.kord.voice.encryption

import com.iwebpp.crypto.TweetNaclFast

internal actual object SecretBox {
    actual val boxzerobytesLength: Int get() = TweetNaclFast.SecretBox.boxzerobytesLength
    actual val zerobytesLength: Int get() = TweetNaclFast.SecretBox.boxzerobytesLength
    actual val nonceLength: Int get() = TweetNaclFast.SecretBox.nonceLength
}

internal actual object Box {
    actual val zerobytesLength: Int get() = TweetNaclFast.Box.zerobytesLength
}

internal actual fun crypto_secretbox(c: ByteArray, m: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int = TweetNaclFast.crypto_secretbox(c, m, d, n, k)
internal actual fun crypto_secretbox_open(m: ByteArray, c: ByteArray, d: Int, n: ByteArray, k: ByteArray): Int = TweetNaclFast.crypto_secretbox_open(m, c, d, n, k)
