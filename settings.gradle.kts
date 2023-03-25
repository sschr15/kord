plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "kord"

include(
    "bom",
    "common",
    "core",
    "gateway",
    "ksp-annotations",
    "ksp-processors",
    "rest",
    "voice",
    "core-voice",
    "test-kit",
    "samples",
    "core:live-tests"
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
