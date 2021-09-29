site:
  title: "${projectTitle}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main/public"
      script: "build/js/packages/${projectName}/kotlin/${projectName}.js"

  port: 8080