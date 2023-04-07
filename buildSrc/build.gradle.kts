plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(libs.bundles.pluginsForBuildSrc)
    implementation("dev.kord:kotlin-multiplatform-tools:0.0.1")
}
