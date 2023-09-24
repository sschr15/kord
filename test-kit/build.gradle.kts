plugins {
    `kord-internal-multiplatform-module`
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.common)
                api(libs.bundles.test.common)
                api(libs.ktor.utils)
            }
        }
        jsMain {
            dependencies {
                api(libs.bundles.test.js)
            }
        }
        jvmMain {
            dependencies {
                api(libs.bundles.test.jvm)
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
