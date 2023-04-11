plugins {
    `kord-multiplatform-module`
    `kord-publishing`
}

kotlin {
    jvm {
        withJava()
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
