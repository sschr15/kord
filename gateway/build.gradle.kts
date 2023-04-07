import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.targets.native.*

plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    configureTargets {
        nodejs {
            dependencies {
                implementation(libs.kotlinx.nodejs)
                implementation(npm("fast-zlib", libs.versions.fastZlib.get()))
            }
        }

        group("native") {
            mingwX64("mingw")
            linuxX64("linux")
            darwin()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.common)

                api(libs.bundles.ktor.client.serialization)
                api(libs.ktor.client.websockets)
                api(libs.ktor.client.core)
            }
        }
    }
}
