<#if !useServer?boolean>// </#if>import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    <#if !useMarkdown?boolean>// </#if>alias(libs.plugins.kobwebx.markdown)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
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
    <#if !useServer?boolean>/*</#if>
    jvm {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "11"
        }

        tasks.named("jvmJar", Jar::class.java).configure {
            archiveFileName.set("${projectName}.jar")
        }
    }
    <#if !useServer?boolean>*/</#if>
    js(IR) {
        moduleName = "${projectName}"
        browser {
            commonWebpackConfig {
                outputFileName = "${projectName}.js"
            }
        }
        binaries.executable()
    }
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

        <#if !useServer?boolean>/*</#if>
        val jvmMain by getting {
            dependencies {
                implementation(libs.kobweb.api)
             }
        }
        <#if !useServer?boolean>*/</#if>
    }
}