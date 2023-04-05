package dev.kord.gateway

import io.ktor.websocket.*
import kotlinx.cinterop.*
import platform.zlib.*

private const val MAX_WBITS = 15 // Maximum window size in bits

internal actual class Inflater {
    // create a shared zlib context
    private val zStream = memScoped {
        alloc<z_stream>().apply {
            inflateInit2(ptr, MAX_WBITS + 16)
        }
    }

    actual fun Frame.inflateData(): String {
        val compressedData = data
        val uncompressedData = memScoped {
            val uncompressedDataSize = 10 * compressedData.size // allocate enough space for the uncompressed data
            val uncompressedData = allocArray<ByteVar>(uncompressedDataSize)
            try {
                zStream.apply {
                    next_in = compressedData.refTo(0).getPointer(memScope).reinterpret()
                    avail_in = compressedData.size.toUInt()
                    next_out = uncompressedData.reinterpret()
                    avail_out = uncompressedDataSize.toUInt()
                }

                val resultCode = inflate(zStream.ptr, Z_FINISH)
                if (resultCode != Z_STREAM_END) {
                    throw IllegalStateException("An error occurred during decompression of frame")
                }
            } finally {
                inflateEnd(zStream.ptr)
            }

            uncompressedData.readBytes(uncompressedDataSize - zStream.avail_out.toInt())
        }

        return uncompressedData.decodeToString()
    }
}
