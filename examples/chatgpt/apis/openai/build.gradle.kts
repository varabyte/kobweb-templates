plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "openai-api"
version = "1.0-SNAPSHOT"

dependencies {
    api(libs.bundles.okhttp)
    api(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines)
}
