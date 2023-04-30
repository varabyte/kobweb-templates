import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.kobwebx.markdown)
}

group = "chat.core"
version = "1.0-SNAPSHOT"

kotlin {
    // Even though this module doesn't actually define any server routes itself, 'includeServer = true' allows us to
    // depened on ":core" as a commonMain dependency from other modules, instead of a JS-only dependency.
    configAsKobwebLibrary(includeServer = true)

    @Suppress("UNUSED_VARIABLE") // Suppress spurious warnings about sourceset variables not being used
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