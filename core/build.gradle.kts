plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    js {
        nodejs {
            testTask(Action {
                useMocha {
                    timeout = "10000" // KordEventDropTest is too slow for default 2 seconds timeout
                }
            })
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
        mingwX64Main {
            dependencies {
                implementation(projects.kspAnnotations)
            }
        }
        linuxX64Main {
            dependencies {
                implementation(projects.kspAnnotations)
            }
        }
    }
}

tasks.getByName("compileKotlinLinuxX64").dependsOn(":core:kspCommonMainKotlinMetadata")
tasks.getByName("compileKotlinMingwX64").dependsOn(":core:kspCommonMainKotlinMetadata")

tasks {
    dokkaHtmlMultiModule {
        enabled = false
    }
}
