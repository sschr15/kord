@file:OptIn(ExperimentalForeignApi::class, KordInternal::class)

package dev.kord.test

import dev.kord.common.WindowsError
import dev.kord.common.annotation.KordInternal
import dev.kord.common.orThrow
import io.ktor.utils.io.*
import kotlinx.cinterop.*
import platform.windows.*

private val baseDir by lazy {
    getEnv("PROJECT_ROOT") ?: error("PROJECT_ROOT environment variable not set")
}

actual fun getEnv(name: String): String? = memScoped {
    val buffer = allocArray<WCHARVar>(1024)
    val result = GetEnvironmentVariableW(name, buffer, 1024u)
    if (result == 0u) {
        throw WindowsError(GetLastError())
    }
    if (result <= 1024u) {
        return@memScoped buffer.toKString()
    }

    val buffer2 = allocArray<WCHARVar>(result.toLong())
    val result2 = GetEnvironmentVariableW(name, buffer2, result)
    if (result2 == 0u) return null // concern this shouldn't happen

    return@memScoped buffer2.toKString()
}

actual suspend fun file(project: String, path: String): String {
    memScoped {
        val handle = CreateFileW(
            "$baseDir/$project/src/commonTest/resources/resources/$path",
            GENERIC_READ,
            FILE_SHARE_READ.toUInt(),
            null,
            OPEN_EXISTING.toUInt(),
            FILE_ATTRIBUTE_NORMAL.toUInt(),
            null
        ) ?: error("Unexpected null handle")

        if (handle == INVALID_HANDLE_VALUE) {
            throw WindowsError(GetLastError())
        }

        val size = alloc<LARGE_INTEGER>()
        GetFileSizeEx(handle, size.ptr).orThrow()

        val buffer = allocArray<ByteVar>(size.reinterpret<LONGLONGVar>().value)
        ReadFile(handle, buffer, size.reinterpret<LONGLONGVar>().value.toUInt(), null, null).orThrow()

        return buffer.toKString()
    }
}

actual suspend fun readFile(project: String, path: String): ByteReadChannel {
    memScoped {
        val handle = CreateFileW(
            "$baseDir/$project/src/commonTest/resources/resources/$path",
            GENERIC_READ,
            FILE_SHARE_READ.toUInt(),
            null,
            OPEN_EXISTING.toUInt(),
            FILE_ATTRIBUTE_NORMAL.toUInt(),
            null
        ) ?: error("Unexpected null handle")

        if (handle == INVALID_HANDLE_VALUE) {
            throw WindowsError(GetLastError())
        }

        val size = alloc<LARGE_INTEGER>()
        GetFileSizeEx(handle, size.ptr).orThrow()

        val buffer = allocArray<ByteVar>(size.reinterpret<LONGLONGVar>().value)
        ReadFile(handle, buffer, size.reinterpret<LONGLONGVar>().value.toUInt(), null, null).orThrow()

        return ByteReadChannel(buffer.readBytes(size.reinterpret<LONGLONGVar>().value.toInt()))
    }
}
