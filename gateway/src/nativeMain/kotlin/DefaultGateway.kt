package dev.kord.gateway

import dev.kord.common.annotation.KordInternal
import io.ktor.util.network.*

@KordInternal
public actual fun Throwable.isTimeout(): Boolean = this is UnresolvedAddressException
internal actual val os: String get() = Platform.osFamily.name
