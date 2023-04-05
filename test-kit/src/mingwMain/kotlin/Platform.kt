package dev.kord.test

import io.ktor.utils.io.*
import kotlinx.cinterop.toKString
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use
import platform.posix.getenv
import kotlin.native.Platform

actual object Platform {
    private val darwinFamily = listOf(OsFamily.IOS, OsFamily.MACOSX, OsFamily.TVOS, OsFamily.WATCHOS)
    actual const val IS_JVM: Boolean = false
    actual const val IS_NODE: Boolean = false
    actual const val IS_BROWSER: Boolean = false
    actual val IS_MINGW: Boolean = Platform.osFamily == OsFamily.WINDOWS
    actual val IS_LINUX: Boolean = Platform.osFamily == OsFamily.LINUX
    actual val IS_DARWIN: Boolean = Platform.osFamily in darwinFamily
}

actual fun getEnv(name: String): String? = getenv(name)?.toKString()
actual suspend fun file(project: String, path: String): String = fsRead(project, path).use { fileSource ->
    fileSource.buffer().use { buffer ->
        buffer.readUtf8()
    }
}

actual suspend fun readFile(project: String, path: String): ByteReadChannel {
    val byteArray = fsRead(project, path).use { fileSource ->
        fileSource.buffer().use { buffer ->
            buffer.readByteArray()
        }
    }

    return ByteReadChannel(byteArray)
}

private fun fsRead(project: String, path: String) =
    FileSystem.SYSTEM.source("${getEnv("PROJECT_ROOT")}/$project/src/commonTest/resources/$path".toPath())
