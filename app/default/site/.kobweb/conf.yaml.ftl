site:
  title: "${escapeYamlString(projectTitle)}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main/public"
      script: "build/kotlin-webpack/js/developmentExecutable/${projectName}.js"
      api: "build/libs/${projectName}.jar"
    prod:
      script: "build/kotlin-webpack/js/productionExecutable/${projectName}.js"
      siteRoot: ".kobweb/site"

  port: 8080
