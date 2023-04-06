plugins {
    //`kord-publishing`
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.kotlin.plugin.serialization
    `kotlinx-atomicfu`
    com.google.devtools.ksp
}

kotlin {
    explicitApi()
    jvm {
        withJava()
    }

    sourceSets {
        all {
            applyKordOptIns()
        }
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                api(kotlin("stdlib-common"))
                api(projects.common)
                api(projects.gateway)

                compileOnly(projects.kspAnnotations)

                api(libs.ktor.network)
                implementation("org.jetbrains.kotlinx:atomicfu:0.20.1")

            }
        }
    }
}

dependencies {
    kspCommonMainMetadata(projects.kspProcessors)
}

tasks {
    afterEvaluate {
        "compileKotlinJvm" {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}
