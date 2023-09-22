package dev.kord.common

import dev.kord.common.annotation.KordInternal
import kotlin.reflect.KClass

/** @suppress */
public actual typealias Class<T> = ArrayDeque<T>

/** @suppress */
@Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@kotlin.internal.InlineOnly
@KordInternal
public actual inline val <T : Any> KClass<T>.java: Class<T>
    inline get() = throw UnsupportedOperationException("'getDeclaringClass()' was never present on Kotlin/JS")
