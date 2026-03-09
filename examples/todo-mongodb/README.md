This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/todo-mongodb` template.

The purpose of this project is to showcase a minimal Todo app, demonstrating:

* a simple, reactive, single-page web app, making use of both Silk UI and Compose for Web
* API endpoints (e.g. for adding, removing, and fetching items)
* how to share types across client and server (see `TodoItem` which has text and an ID value)
* how to use the MongoDB database to persist todos, using the [KtMongo library](https://ktmongo.opensavvy.dev)

I'd like to give credit to https://blog.upstash.com/nextjs-todo for sharing the Next.js version.

---

To run the sample, simply enter the following commands in the terminal:

```bash
$ docker run -d -p '27017:27017' 'mongo:8.0.6'  # This will print the container ID when it's started
$ cd site
$ kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

To stop MongoDB, use `docker ps` to find its container ID, then `docker stop <container-id>`.
