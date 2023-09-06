site:
  title: "${projectTitle}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main/public"
      script: "build/dist/js/developmentExecutable/${projectName}.js"
      api: "build/libs/${projectName}.jar"
    prod:
      script: "build/dist/js/productionExecutable/${projectName}.js"
      siteRoot: ".kobweb/site"

  port: 8080
