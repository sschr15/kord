package dev.kord.gateway

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

public value class Value(public val value: Int)

public fun main() {
    val atomic = atomic(Value(1))
    println(atomic)
    atomic.update { Value(2) }
    println(atomic)
}
