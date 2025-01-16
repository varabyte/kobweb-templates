plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "imageprocessor.util"
version = "1.0-SNAPSHOT"

kotlin {
    js { browser() }

    sourceSets {
        jsMain.dependencies {
        }
    }
}
