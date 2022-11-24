import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.kobweb.application) apply false
    alias(libs.plugins.kobweb.library) apply false
    alias(libs.plugins.kobwebx.markdown) apply false
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }

    val versionStr = JavaVersion.VERSION_11.toString()
    tasks.withType<JavaCompile> {
        sourceCompatibility = versionStr
        targetCompatibility = versionStr
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = versionStr
        }
    }
}
