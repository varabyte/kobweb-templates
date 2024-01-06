import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.library)
    <#if !useMarkdown?boolean>// </#if>alias(libs.plugins.kobwebx.markdown)
}

group = "${groupId}"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebLibrary(<#if useServer?boolean>includeServer = true</#if>)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            <#if !useSilk?boolean>// </#if>implementation(libs.kobweb.silk)
            <#if !useMarkdown?boolean>// </#if>implementation(libs.kobwebx.markdown)
        }
        <#if useServer?boolean>
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }
        </#if>
    }
}
