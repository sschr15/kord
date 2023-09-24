package dev.kord.rest.request

actual typealias StackTraceElement = String

actual fun currentThreadStackTrace(): StackTraceElement =
    Exception().stackTraceToString().lineSequence().drop(3).first().trim()

internal actual fun RecoveredStackTrace.validate(expected: StackTraceElement) {
    //TODO actually validate
}
