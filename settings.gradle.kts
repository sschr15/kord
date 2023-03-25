plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "kord"

include(
    "bom",
    "common",
    "core",
    "core:live-tests",
    "core-voice",
    "gateway",
    "ksp-annotations",
    "ksp-processors",
    "rest",
    "samples",
    "test-kit",
    "voice",
    "core-voice",
    "test-kit",
    "samples",
    "core:live-tests"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
