import dev.kord.gradle.model.configureTargets
import dev.kord.gradle.model.group
import dev.kord.gradle.model.targets.native.ios
import dev.kord.gradle.model.targets.native.macos
import dev.kord.gradle.model.targets.native.tvos

plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    configureTargets {
        group("darwin") {
            tvos()
            macos {
                target {
                    binaries.executable()
                }
            }
            ios()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core)
                api(projects.voice)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
                implementation("com.squareup.okio:okio:3.3.0")
            }
        }
    }
}
