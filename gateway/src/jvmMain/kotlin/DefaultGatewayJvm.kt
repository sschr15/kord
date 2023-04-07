package dev.kord.gateway

import dev.kord.common.annotation.KordInternal
import java.nio.channels.UnresolvedAddressException

@KordInternal
public actual fun Throwable.isTimeout(): Boolean = this is UnresolvedAddressException
internal actual val os: String get() = System.getProperty("os.name")
