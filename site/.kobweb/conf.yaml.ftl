site:
  title: "${projectTitle}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main/public"
      script: "build/js/packages/${projectName}/kotlin/${projectName}.js"
      api: "build/libs/${projectName}.jar"
    prod:
      siteRoot: ".kobweb/site"

  port: 8080