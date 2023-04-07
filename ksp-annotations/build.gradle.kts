import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.targets.native.*

plugins {
    `kord-internal-multiplatform-module`

    // workaround for https://youtrack.jetbrains.com/issue/KT-43500 (not intended to be published)
    org.jetbrains.dokka
    `kord-publishing`
}

kotlin {
    configureTargets {
        nodejs()
        linuxX64()
        mingwX64()
        darwin()
    }
}

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    enabled = false
}
