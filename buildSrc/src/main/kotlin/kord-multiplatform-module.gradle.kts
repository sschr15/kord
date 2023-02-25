import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.kotlin.plugin.serialization
    org.jetbrains.dokka
    `kotlinx-atomicfu`
    org.jetbrains.kotlinx.`binary-compatibility-validator`
    com.google.devtools.ksp
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        nodejs()
    }
    jvmToolchain(Jvm.target)

    targets {
        all {
            compilations.all {
                compilerOptions.options.applyKordCompilerOptions()
            }
        }
    }

    sourceSets {
        all {
            languageSettings {
                if ("Test" in name) {
                    optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                }
                listOf(
                    OptIns.time,
                    OptIns.contracts,
                    OptIns.kordPreview,
                    OptIns.kordExperimental,
                    OptIns.kordVoice,
                ).forEach(::optIn)
            }

            repositories {
                // until Dokka 1.8.0 is released and we no longer need dev builds, see https://github.com/kordlib/kord/pull/755
                maven("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
            }
        }
        commonMain {
            // mark ksp src dir
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}

tasks {
    getByName<KotlinJvmTest>("jvmTest") {
        useJUnitPlatform()
    }

    withType<KotlinJsTest>() {
        environment("PROJECT_ROOT", rootProject.projectDir.absolutePath)
    }

    afterEvaluate {
        getByName("compileKotlinJvm") {
            dependsOnKspKotlin("kspCommonMainKotlinMetadata")
        }
        getByName("compileKotlinJs") {
            dependsOnKspKotlin("kspCommonMainKotlinMetadata")
        }
    }

    configureDokka {
        dependsOnKspKotlin("kspCommonMainKotlinMetadata")

        dokkaSourceSets {
            val map = asMap

            if (map.containsKey("jsMain")) {
                named("jsMain") {
                    displayName.set("JS")
                }
            }

            if (map.containsKey("jvmMain")) {
                named("jvmMain") {
                    displayName.set("JVM")
                }
            }

            if (map.containsKey("commonMain")) {
                named("jvmMain") {
                    displayName.set("Common")
                }
            }
        }

    }
}
