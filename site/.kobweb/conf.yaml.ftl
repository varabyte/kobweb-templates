site:
  title: "${projectTitle}"

server:
  locations:
    dev:
      content: "build/processedResources/js/main"
      script: "build/js/packages/${projectName}/kotlin/${projectName}.js"

  port: 8080