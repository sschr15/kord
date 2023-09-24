@file:OptIn(ExperimentalForeignApi::class)

package dev.kord.gateway

import kotlinx.cinterop.*
import platform.posix.uname
import platform.posix.utsname

internal actual fun Throwable.isTimeout(): Boolean = false
internal actual val os by lazy {
    memScoped { 
        val result = alloc<utsname>()
        uname(result.ptr)
        result.sysname.toKString()
    }
}
