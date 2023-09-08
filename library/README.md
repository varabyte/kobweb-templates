This is a [Kobweb](https://github.com/varabyte/kobweb) project bootstrapped with the `library` template.

This template is useful if you want to create a re-usable library that can be consumed by other Kobweb projects. The
biggest difference between a Kobweb library and a Kobweb application is that the library applies the
`com.varabyte.kobweb.library` Gradle plugin instead in its build script.

A very easy way to share your Kobweb library with the world is to use [JitPack](https://jitpack.io/). You can read more
about that approach on their site, but essentially, your steps will be:

* Edit your library's build script, adding the `maven-publish` plugin.
* Make sure the build script group and versions are set to what you want (or, optionally, configure a publishing block).
* Double check that this is working by running `publishToMavenLocal` from the command line.
* Commit your changes and push them to GitHub.
* In a different project, which will consume your library, add the `jitpack.io` repository and then add a dependency
  to your library's group and artifact:
  ```kotlin
  repositories {
      maven(url = "https://jitpack.io")
  }
  dependencies {
      implementation("group.path.here:project-name-here:<version-here>")
  }
  ```

For a concrete example, you can refer to
this [Kotlin Boostrap library build script](https://github.com/stevdza-san/KotlinBootstrap/blob/master/bootstrap/build.gradle.kts)
which results in the following [JitPack artifact entry](https://jitpack.io/#stevdza-san/KotlinBootstrap).

This above project opts to provide its own publishing block for more control over the artifact name and version, but if
you omit it, it will try to use reasonable defaults from your project's build script settings instead. The group and
version will come directly from the build script values themselves, and the artifact name will be the name of the
project (usually the folder name, but whatever you set in `settings.gradle.kts`).
