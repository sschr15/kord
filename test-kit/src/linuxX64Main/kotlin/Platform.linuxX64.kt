@file:OptIn(ExperimentalForeignApi::class)

package dev.kord.test

import io.ktor.utils.io.*
import kotlinx.cinterop.*
import platform.posix.*

private val baseDir by lazy {
    getEnv("PROJECT_ROOT") ?: error("PROJECT_ROOT environment variable not set")
}

actual fun getEnv(name: String): String? = getenv(name)?.toKString()

actual suspend fun file(project: String, path: String): String {
    val filename = "$baseDir/$project/src/commonTest/resources/$path"
    val file = fopen(filename, "r") ?: error("Could not open file $filename")
    try {
        return buildString {
            memScoped {
                val bufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(bufferLength)
                while (true) {
                    val next = fgets(buffer, bufferLength, file)?.toKString() ?: break
                    append(next)
                }
            }
        }
    } finally {
        fclose(file)
    }
}

actual suspend fun readFile(project: String, path: String): ByteReadChannel {
    val filename = "$baseDir/$project/src/commonTest/resources/$path"
    val file = fopen(filename, "r") ?: error("Could not open file $filename")
    try {
        val size = memScoped {
            val st = alloc<stat>()
            fstat(fileno(file), st.ptr)
            st.st_size
        }
        return memScoped { 
            val buffer = allocArray<ByteVar>(size)
            fread(buffer, size.toULong(), 1u, file)
            ByteReadChannel(buffer.readBytes(size.toInt()))
        }
    } finally {
        fclose(file)
    }
}
