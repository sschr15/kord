import dev.kord.gradle.model.configureTargets
import dev.kord.gradle.model.targets.native.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.*

plugins {
    `kord-internal-multiplatform-module`
}

kotlin {
    configureTargets {
        nodejs {
            dependencies {
                api(libs.bundles.test.js)
            }
        }
        group("native") {
            dependencies {
                api(libs.okio)
            }
            linuxX64()
            mingwX64()
            darwin()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.bundles.test.common)
                api(libs.ktor.utils)
            }
        }
        jvmMain {
            dependencies {
                api(libs.bundles.test.jvm)
            }
        }
    }
}

