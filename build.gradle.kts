plugins {
    org.jetbrains.dokka // for dokkaHtmlMultiModule task
}

allprojects {
    repositories {
        mavenLocal() // custom build of kord-cache
        mavenCentral()
    }
}

group = Library.group
version = libraryVersion
