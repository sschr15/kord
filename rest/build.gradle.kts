import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.targets.native.*

plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    configureTargets {
        nodejs()
        group("native") {
            linuxX64("linux")
            mingwX64("mingw")
            darwin()
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(projects.common)

                api(libs.bundles.ktor.client.serialization)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.ktor.client.mock)
            }
        }
    }
}
