import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9

plugins {
    `kord-internal-module`
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
        // K2 isn't happy with the ksp-processors project (probably because of context receivers)
        languageVersion = KOTLIN_1_9
        freeCompilerArgs.addAll("-Xskip-prerelease-check", "-Xallow-unstable-dependencies")
    }
}

dependencies {
    implementation(projects.kspAnnotations)

    implementation(libs.ksp.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)

    implementation(libs.kotlinx.serialization.json) // use types directly
}
