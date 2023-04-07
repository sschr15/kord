package dev.kord.gateway

import dev.kord.common.annotation.KordInternal
import io.ktor.client.plugins.websocket.*
import node.process.process

@KordInternal
public actual fun Throwable.isTimeout(): Boolean = this is WebSocketException && "ENOTFOUND" in toString()
internal actual val os: String get() = process.platform.toString()
