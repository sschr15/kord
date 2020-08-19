package com.gitlab.kordlib.core.builder.kord

import com.gitlab.kordlib.common.entity.Snowflake
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.header
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.util.*

internal fun HttpClientConfig<*>.defaultConfig(token: String) {
    expectSuccess = false
    defaultRequest {
        header("Authorization", "Bot $token")
    }

    install(JsonFeature)
    install(WebSockets)
}

internal fun HttpClient?.configure(token: String): HttpClient {
    if (this != null) return this.config {
        defaultConfig(token)
    }

    @OptIn(UnstableDefault::class)
    val jsonConfig = JsonConfiguration(
            encodeDefaults = false,
            allowStructuredMapKeys = true,
            ignoreUnknownKeys = true,
            isLenient = true
    )

    val json = Json(jsonConfig)

    return HttpClient(CIO) {
        defaultConfig(token)
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }
}


internal fun getBotIdFromToken(token: String): Snowflake {
    try {
        val bytes = Base64.getDecoder().decode(token.split(""".""").first())
        return Snowflake(String(bytes))
    } catch (exception: IllegalArgumentException) {
        throw IllegalArgumentException("Malformed bot token: '$token'. Make sure that your token is correct.")
    }
}

