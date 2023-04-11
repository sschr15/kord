plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven("https://kord.jfrog.io/artifactory/gradle-dev")
}

dependencies {
    implementation(libs.bundles.pluginsForBuildSrc)
}
