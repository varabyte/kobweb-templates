import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.library)
    <#if !useMarkdown?boolean>// </#if>alias(libs.plugins.kobwebx.markdown)
}

group = "${groupId}"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebLibrary(<#if useServer?boolean>includeServer = true</#if>)

    sourceSets {
        <#if useServer?boolean>
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here
//        }
        </#if>
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
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
