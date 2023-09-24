plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
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

tasks.getByName("compileKotlinLinuxX64").dependsOn(":rest:kspCommonMainKotlinMetadata")
tasks.getByName("compileKotlinMingwX64").dependsOn(":rest:kspCommonMainKotlinMetadata")
