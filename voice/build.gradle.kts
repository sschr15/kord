import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.native.*

plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    jvm {
        withJava()
    }
    configureTargets {
        group("native") {
            dependencies {
                implementation("com.ionspin.kotlin:multiplatform-crypto-libsodium-bindings:0.8.9")
                implementation("com.squareup.okio:okio:3.3.0")

            }
            group("darwin") {
                tvos()
                macos()
                ios()
            }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                api(projects.common)
                api(projects.gateway)

                compileOnly(projects.kspAnnotations)

                api(libs.ktor.network)
            }
        }
    }
}
