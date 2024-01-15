import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
}

group = "counter"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebApplication()

    sourceSets {
        jsMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
        }
    }
}
