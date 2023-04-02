This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/chatgpt` template.

The purpose of this project is to showcase a minimal ChatGPT app, using a Kobweb server as a way to send clients useful
responses from ChatGPT without exposing your OpenAI key to them. 

### Project organization

The project is laid out as follows:

* `commonMain` - Some models used for communicating back and forth between the client and server. This is accomplished
  by serializing those classes as JSON over the wire.
* `jsMain` - The client-side code, with most of the logic living in `Index.kt`. This is responsible for collecting input
  from the user, sending it to the server, and displaying the response.
* `jvmMain` - The server-side code. There is some generally useful (though still barebones) logic for communicating with
  the ChatGPT APIs in `OpenAi.kt`. In production, you may want to find an external library to use instead, but it was
  included here for clarity in this demo. The logic that receives the user's text and sends it to ChatGPT lives in
  `Chat.kt`.

### Open AI key

This project includes your *private* OpenAI key, placed under [the JVM resources directory](src/jvmMain/resources/openaikey.txt).
For your protection, this file has been added into the project's `.gitignore` file. However, this means if you check
this project into source control and sync it elsewhere, it will not work.

Please be very careful with your private key! **You should never upload it to a public location.**

If you want to run a middle-man server like this in production, with the client on one side and ChatGTP on the other,
you'll need to figure out how to include this private key in your project. A common approach is to use something like
a secret stored with the service you're using to host your project. You could also simply check the key in if your
project is a personal one and kept inside a private repository, but this is not recommended as someone may set the
repository to public in the future without realizing what they've exposed.

### Not meant for production

This project serves as a basic sample but would need to be improved if built upon for production.

* As stated above, you'll need to figure out a better place to store your private key.
* You probably don't want chat history to live directly in memory, but rather consider using a database with
  configurable caching lifetimes. Or, at least update the logic to occasionally remove state histories.
* The current logic for counting tokens in the user's message is naive. OpenAI has a library for this, which is not
  currently available in Kotlin [(see here for the Python library)](https://huggingface.co/docs/transformers/model_doc/gpt2#transformers.GPT2TokenizerFast).
  I sidestepped worrying about this for this example, but it might be important to figure out in a real production app.
* You'll probably want to add some kind of rate limiting to prevent abuse.
* You'll probably want to add some kind of authentication to prevent abuse. 
* The client should consider saving chat histories locally. This would allow the user to refresh the page without
  losing their chat history.

---

To run the sample, simply enter the following command in the terminal:

```bash
kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.
