This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/todo` template.

The purpose of this project is to showcase a minimal Todo app, demonstrating:

* a simple, reactive, single-page web app, making use of both Silk UI and Compose for Web
* API endpoints (e.g. for adding, removing, and fetching items)
* how to send types between client and server (see `TodoItem` which has text and an ID value, and which gets serialized
  and sent over the wire)

I'd like to give credit to https://blog.upstash.com/nextjs-todo for sharing the Next.js version.

## Run

To run the sample, simply enter the following commands in the terminal:

```bash
# If you are using MongoDB, make sure it is running before you do this!
# More about this shortly.
$ cd site
$ kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

## Datastore Implementations

The server provides two implementations for the data store that stores a user's todo data:

* a simple in-memory version (gets wiped out every time the server is shut down and restarted)
* a MongoDB version (saves data persistently but user is responsible for starting up a DB docker instance first)

If you want to change which version is used, update which system property value is getting set in the build script (by
toggling which line is commented out):

```kotlin
// site/build.gradle.kts
kobweb {
    app {
        server {
            systemProperties.put("todo.datastore.impl",
                "InMemory",
                // "MongoDb",
            )
        }
    }
}
```

Normally, you would not support two different implementations on your own backend -- it is more common to use a specific
database provider and then toggle between a dev instance or a prod one (based on `ctx.env`).

However, our approach lets us provide a working fullstack example that is easy to run out of the box without requiring
users to fidget with Docker. Only people who are interested in seeing a DB in action need to opt in to the (minimally)
extra work.

## Using Mongo DB

To run MongoDB, in your terminal, before calling `kobweb run`, enter the following command:

```bash
$ docker compose up -d
```

To see the MongoDB logs, use the following command:
```bash
$ docker compose logs -f
```

When you are done running the example, you should also remember to stop docker.

```bash
$ docker compose down
```

## KtMongo

[KtMongo](https://github.com/CLOVIS-AI/KtMongo) (pronounced "Cat Mongo") is a state-of-the-art, Kotlin-idiomatic library
for wrapping MongoDB APIs with a type-safe DSL.

This todo example uses the coroutines flavor of KtMongo, which plays nicely with Kobweb's endpoints (which are all
`suspend` methods).

If you are considering using MongoDB in your own Kotlin project, we highly recommend you choose KtMongo and
[read the excellent documentation](https://ktmongo.opensavvy.dev/)!
