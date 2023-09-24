package dev.kord.gateway

import dev.kord.common.WindowsError
import platform.windows.*
import platform.winhttp.ERROR_WINHTTP_TIMEOUT

private val timeoutErrors = setOf(
    ERROR_TIMEOUT,
    ERROR_SEM_TIMEOUT,
    ERROR_INTERNET_TIMEOUT,
    ERROR_DS_TIMELIMIT_EXCEEDED,
    ERROR_COUNTER_TIMEOUT,
    ERROR_WINHTTP_TIMEOUT,
    DNS_ERROR_RECORD_TIMED_OUT,
).map { it.toUInt() }

internal actual fun Throwable.isTimeout(): Boolean = this is WindowsError && code in timeoutErrors

internal actual val os = "Windows"
