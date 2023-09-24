@file:OptIn(ExperimentalForeignApi::class)

package dev.kord.common

import dev.kord.common.annotation.KordInternal
import kotlinx.cinterop.*
import platform.windows.*

@KordInternal
public fun WINBOOL.orThrow(): WINBOOL {
    if (this == 0) {
        throw WindowsError(GetLastError())
    }
    return this
}

@KordInternal
public fun genErrorString(code: UInt): String? {
    memScoped { 
        val flags = FORMAT_MESSAGE_ALLOCATE_BUFFER or FORMAT_MESSAGE_FROM_SYSTEM or FORMAT_MESSAGE_IGNORE_INSERTS
        val bufferLocation = alloc<LPWSTRVar>()
        FormatMessageW(
            flags.toUInt(),
            null, // use Windows internal strings
            code.toUInt(),
            0u, // use detected language
            bufferLocation.ptr.reinterpret(), // FORMAT_MESSAGE_ALLOCATE_BUFFER requires a cast ("(LPTSTR)&buffer")
            0u, // let Windows figure out the size
            null // no args
        )
        val buffer = bufferLocation.value ?: return null
        val result = buffer.toKString()
        LocalFree(buffer)
        return result
    }
}

@KordInternal
public class WindowsError(public val code: UInt) : Error(genErrorString(code) ?: "Unknown error code $code")
