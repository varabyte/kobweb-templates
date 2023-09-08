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
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(libs.kobweb.core)
                <#if !useSilk?boolean>// </#if>implementation(libs.kobweb.silk.core)
                <#if !useMarkdown?boolean>// </#if>implementation(libs.kobwebx.markdown)
            }
        }
        <#if useServer?boolean>
        val jvmMain by getting {
            dependencies {
                implementation(libs.kobweb.api)
            }
        }
        </#if>
    }
}
