site:
  title: "${projectTitle}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main/public"
      script: "build/developmentExecutable/${projectName}.js"
      api: "build/libs/${projectName}.jar"
    prod:
      script: "build/distributions/${projectName}.js"
      siteRoot: ".kobweb/site"

  port: 8080