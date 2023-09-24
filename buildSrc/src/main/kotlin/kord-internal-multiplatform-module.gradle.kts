import gradle.kotlin.dsl.accessors._bab2ceb4f745db29783df2fe8283e16d.commonMain

plugins {
    org.jetbrains.kotlin.multiplatform
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(IR) {
        nodejs()
    }
    jvmToolchain(Jvm.target)
    linuxX64()
    mingwX64()

    targets {
        all {
            compilations.all {
                compilerOptions.options.applyKordCompilerOptions()
            }
        }
    }
    
    sourceSets {
        val nativeMain by creating {
            dependsOn(commonMain.get())
        }
        targets
            .map { it.name }
            .filter { it != "jvm" && it != "metadata" && it != "js" }
            .forEach { target ->
                sourceSets.getByName("${target}Main") {
                    dependsOn(nativeMain)
                }
            }
    }
}
