package dev.kord.gateway

internal actual fun Throwable.isTimeout() = false
internal actual val os: String get() = Platform.osFamily.name
