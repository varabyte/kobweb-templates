// Add compose gradle plugin
plugins {
    kotlin("multiplatform") version "1.5.30"
    id("org.jetbrains.compose") version "1.0.0-alpha4-build331"
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

                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
             }
        }
    }
}