import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    <#if !useMarkdown?boolean>// </#if>alias(libs.plugins.kobwebx.markdown)
}

group = "${groupId}"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    <#if !useServer?boolean>
    configAsKobwebApplication("${projectName}")
    <#else>
    configAsKobwebApplication("${projectName}", includeServer = true)
    jvmToolchain(11) // Kobweb server should use at least Java 11
    </#if>

    @Suppress("UNUSED_VARIABLE") // Suppress spurious warnings about sourceset variables not being used
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(libs.kobweb.core)
                <#if !useSilk?boolean>// </#if>implementation(libs.kobweb.silk.core)
                <#if !useSilk?boolean>// </#if>implementation(libs.kobweb.silk.icons.fa)
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