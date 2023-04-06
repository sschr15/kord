package dev.kord.common.http

import dev.kord.common.annotation.KordInternal
import io.ktor.client.engine.*
import io.ktor.client.engine.winhttp.*

@KordInternal
public actual object HttpEngine : HttpClientEngineFactory<HttpClientEngineConfig> by WinHttp

