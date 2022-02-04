This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/jb/counter` template.

It is based of the "Getting Started" tutorial provided by JetBrains
[here](https://github.com/JetBrains/compose-jb/tree/master/tutorials/Web/Getting_Started).

Even though Kobweb provides a extra functionality that you can use, this project doesn't use most of them (note the lack
of dependencies in the project's `build.gradle.kts` file), and that's OK! Being a thin shim around Compose for Web is a
perfectly acceptable use-case for this framework.

---

To run the sample, simply enter the following command in the terminal:

```bash
kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.