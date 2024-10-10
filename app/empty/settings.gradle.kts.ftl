pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }
}

rootProject.name = "${projectName}"

include(":site")
<#if useWorker?boolean>include(":worker")</#if>
