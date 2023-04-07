import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.targets.native.*

@Suppress("DSL_SCOPE_VIOLATION") // false positive for `libs` in IntelliJ
plugins {
    `kord-multiplatform-module`
    `kord-publishing`
    alias(libs.plugins.buildconfig)
}

kotlin {
    configureTargets {
        group("nonJvm") {
            dependencies {
                implementation(libs.ktor.utils)
                implementation(libs.bignum)
                implementation(libs.stately.collections)
            }
            nodejs {
                dependencies {
                    api(libs.ktor.client.js)

                    // workaround for https://youtrack.jetbrains.com/issue/KT-43500
                    // (intended to be compileOnly in commonMain only)
                    implementation(projects.kspAnnotations)
                }
            }
            group("native") {
                dependencies {
                    // K/N doesn't support compileOnly dependencies
                    implementation(projects.kspAnnotations)
                }
                mingwX64("mingw") {
                    dependencies {
                        implementation(libs.ktor.client.winhttp)
                    }
                }
                linuxX64("linux") {
                    dependencies {
                        implementation(libs.ktor.client.curl)
                    }
                }
                darwin {
                    dependencies {
                        implementation(libs.ktor.client.darwin)
                    }
                }
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)
                api(libs.kotlin.logging)

                api(libs.ktor.client.core)

                compileOnly(projects.kspAnnotations)
            }
        }
        jvmMain {
            dependencies {
                api(libs.ktor.client.cio)
            }
        }
    }
}

/*
This will generate a file named "BuildConfigGenerated.kt" that looks like:

package dev.kord.common

internal const val BUILD_CONFIG_GENERATED_LIBRARY_VERSION: String = "<version>"
internal const val BUILD_CONFIG_GENERATED_COMMIT_HASH: String = "<commit hash>"
internal const val BUILD_CONFIG_GENERATED_SHORT_COMMIT_HASH: String = "<short commit hash>"
*/
buildConfig {
    packageName("dev.kord.common")
    className("BuildConfigGenerated")

    useKotlinOutput {
        topLevelConstants = true
        internalVisibility = true
    }

    buildConfigField("String", "BUILD_CONFIG_GENERATED_LIBRARY_VERSION", "\"${Library.version}\"")
    buildConfigField("String", "BUILD_CONFIG_GENERATED_COMMIT_HASH", "\"${Library.commitHash}\"")
    buildConfigField("String", "BUILD_CONFIG_GENERATED_SHORT_COMMIT_HASH", "\"${Library.shortCommitHash}\"")
}
