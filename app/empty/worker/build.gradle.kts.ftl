import com.varabyte.kobweb.gradle.worker.util.configAsKobwebWorker

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.worker)
    alias(libs.plugins.kotlinx.serialization)
}

group = "${groupId}.worker"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebWorker()

    sourceSets {
        jsMain.dependencies {
            implementation(libs.kobweb.worker)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kobwebx.serialization.kotlinx)
        }
    }
}
