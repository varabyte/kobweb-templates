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
    configAsKobwebApplication("${projectName}"<#if useServer?boolean>, includeServer = true</#if>)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            <#if !useSilk?boolean>// </#if>implementation(libs.kobweb.silk)
            <#if !useSilk?boolean>// </#if>implementation(libs.silk.icons.fa)
            <#if !useMarkdown?boolean>// </#if>implementation(libs.kobwebx.markdown)
        }
        <#if useServer?boolean>
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }
        </#if>
    }
}
