import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import dev.kord.gradle.model.*
import dev.kord.gradle.model.targets.*

plugins {
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.kotlin.plugin.serialization
    org.jetbrains.dokka
    `kotlinx-atomicfu`
    org.jetbrains.kotlinx.`binary-compatibility-validator`
    com.google.devtools.ksp
    dev.kord.`kotlin-multiplatform-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    kspCommonMainMetadata(project(":ksp-processors"))
}

apiValidation {
    applyKordBCVOptions()
}

kotlin {
    explicitApi()
    jvmToolchain(Jvm.target)

    configureTargets {
        jvm()
    }

    targets.all {
        compilations.all {
            compilerOptions.options.applyKordCompilerOptions()
        }
    }

    sourceSets {
        all {
            applyKordOptIns()
        }
        commonMain {
            // mark ksp src dir
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonTest {
            dependencies {
                implementation(project(":test-kit"))
            }
        }
    }
}

kotlinMultiplatformProject {
    configurePublishing.set(false)
}

configureAtomicFU()

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    withType<KotlinJsTest>().configureEach {
        environment("PROJECT_ROOT", rootProject.projectDir.absolutePath)
    }
    withType<KotlinNativeTest>().configureEach {
        environment("PROJECT_ROOT", rootProject.projectDir.absolutePath)
    }

    // Configuring before/after evaluate, to mute warning and catch after evaluate changes
    configureNonJvmSourceSet()
    afterEvaluate {
        configureNonJvmSourceSet()
    }

    withType<AbstractDokkaLeafTask>().configureEach {
        applyKordDokkaOptions()
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

fun Project.configureNonJvmSourceSet() {
    project.kotlin.targets
        .asSequence()
        .map { it.name }
        .filterNot { it == "metadata" }
        .flatMap {
            val capitalized = it[0].uppercase() + it.drop(1)
            listOf("compileKotlin$capitalized", "${it}SourcesJar")
        }.forEach { task ->
            tasks.findByName(task)?.apply {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
}
