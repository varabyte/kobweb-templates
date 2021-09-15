// Add compose gradle plugin
plugins {
    kotlin("multiplatform") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha1"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
}

group = "${groupId}"
version = "1.0-SNAPSHOT"

// Enable JS(IR) target and add dependencies
kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.runtime)

                implementation("com.varabyte.kobweb:kobweb:0.3.0-SNAPSHOT")
                implementation("com.varabyte.kobweb:kobweb-silk:0.3.0-SNAPSHOT")
                implementation("com.varabyte.kobweb:kobweb-silk-icons-fa:1.0-SNAPSHOT")
             }
        }
    }
}
