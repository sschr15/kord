plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    mingwX64("mingw")
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
