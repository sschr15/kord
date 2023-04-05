plugins {
    `kord-internal-multiplatform-module`
}

kotlin {
    mingwX64("mingw")
    sourceSets {
        commonMain {
            dependencies {
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

        getByName("mingwMain") {
            dependencies {
                api(libs.okio)
            }
        }
    }
}
