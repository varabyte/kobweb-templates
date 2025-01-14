import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.kobwebx.markdown)
}

group = "chat.core"
version = "1.0-SNAPSHOT"

kotlin {
    // Even though this module doesn't actually define any server routes itself, 'includeServer = true' allows us to
    // depened on ":core" as a commonMain dependency from other modules, instead of a JS-only dependency.
    configAsKobwebLibrary(includeServer = true)

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
        }
    }
}
