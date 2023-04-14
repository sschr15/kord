package dev.kord.gateway

import dev.kord.common.annotation.KordInternal

@KordInternal
public actual fun Throwable.isTimeout(): Boolean = false
internal actual val os: String get() = Platform.osFamily.name
