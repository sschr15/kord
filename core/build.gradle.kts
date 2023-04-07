import dev.kord.gradle.model.configureTargets
import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*
import dev.kord.gradle.model.targets.native.*

plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    configureTargets {
        group("nonJvm") {
            js {
                target {
                    nodejs {
                        testTask {
                            useMocha {
                                timeout = "10000" // KordEventDropTest is too slow for default 2 seconds timeout
                            }
                        }
                    }
                }
            }

            linuxX64()
            mingwX64()
            darwin()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.common)
                api(projects.rest)
                api(projects.gateway)

                api(libs.kord.cache.api)
                api(libs.kord.cache.map)
            }
        }
        jvmTest {
            dependencies {
                implementation(libs.mockk)
            }
        }
    }
}

tasks {
    dokkaHtmlMultiModule {
        enabled = false
    }
}
