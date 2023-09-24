@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package dev.kord.gateway

import io.ktor.utils.io.core.*
import io.ktor.websocket.*
import kotlinx.cinterop.*
import platform.zlib.*

internal actual class Inflater actual constructor() : Closeable {
    actual fun Frame.inflateData(): String {
        memScoped { 
            val bytes = data.asUByteArray()
            val result = mutableListOf<Byte>()
            val out = allocArray<UByteVar>(1024)
            val stream = alloc<z_stream> {
                next_in = bytes.refTo(0).getPointer(this@memScoped)
                avail_in = data.size.toUInt()
                next_out = out
                avail_out = 1024u
                zalloc = null
                zfree = null
                opaque = null
            }

            inflateInit(stream.ptr)

            while (inflate(stream.ptr, Z_SYNC_FLUSH) == Z_OK) {
                result.addAll(out.reinterpret<ByteVar>().readBytes(stream.total_out.toInt()).toTypedArray())
                stream.next_out = out
                stream.avail_out = 1024u
            }

            if (stream.avail_out != 1024u) {
                result.addAll(out.reinterpret<ByteVar>().readBytes(stream.total_out.toInt()).toTypedArray())
            }

            inflateEnd(stream.ptr)

            return result.toByteArray().decodeToString()
        }
    }

    override fun close() {
        // cool, nothing to do here
    }
}
