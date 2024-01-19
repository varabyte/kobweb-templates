This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/imageprocessor` template.

The purpose of this project is to showcase a reasonably sophisticated web worker implementation.

Normally, most web worker articles discuss something very simple -- a worker that receives a numeric value, does some
calculation on it, and returns a new value.

However, using sealed classes, you can create a worker that can receive a variety of commands, and return a variety of
results. (Of course, Kobweb Workers support communicating via simple, direct value types as well).

In this site, we create a simple "Image Processor" worker that can apply effects onto an image. An advantage of this
approach is it provides a strong guarantee that the logic inside the worker cannot ever affect the UI directly.

In a real project, such a separation of responsibilities could be a good way to divide responsibilities across multiple
developers. The fact this approach lets you write complex, computationally intensive logic without worrying about
affecting the responsiveness of your site is just icing on the cake.

## Project organization

The project is divided up into three modules: `site`, `worker`, and `util`.

In the `worker` module, we defined the `ImageProcessorWorkerFactory` class. The Kobweb Worker Gradle plugin will
automatically generate an `ImageProcessorWorker` class from this factory. 

We separated the implementation details of the `worker` module out into a separate `util` module to ensure that those
classes won't leak into the final site. (You can of course put this logic inside the `worker` module and tag everything
internal, but that can be easy to forget).

Finally, you can find `ImageProcessorWorker` used by the `site` module in `Index.kt`.

## Running

---

To run the sample, simply enter the following commands in the terminal:

```bash
$ cd site
$ kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.
