plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.kobwebx.markdown)
}

group = "chat.core"
version = "1.0-SNAPSHOT"

kotlin {
    js(IR) {
        browser()
    }
    jvm() // Allows us to include ":core" as a commonMain dependency, even if we don't have any JVM code
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
                implementation(libs.kobweb.silk.icons.fa)
            }
        }
    }
}