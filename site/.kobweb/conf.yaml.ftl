site:
  title: "${projectTitle}"

server:
  files:
    dev:
      contentRoot: "build/processedResources/js/main"
      script: "build/js/packages/${projectName}/kotlin/${projectName}.js"

  port: 8080