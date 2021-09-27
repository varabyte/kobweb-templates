site:
  title: "${projectTitle}"

server:
  locations:
    dev:
      contentRoot: "build/processedResources/js/main"
      script: "build/js/packages/${projectName}/kotlin/${projectName}.js"

  port: 8080