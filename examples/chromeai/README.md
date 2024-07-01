This is a [Kobweb](https://github.com/varabyte/kobweb) project instantiated from the `examples/chrome-ai` template.

The purpose of this project is to showcase a minimal on device ai example with streaming text response using [chrome-ai](https://github.com/dead8309/chrome-ai) library.

> [!IMPORTANT]  
> Check [how to enable AI in Chrome](#how-to-enable-ai-in-chrome) first before running this example.

---

To run the sample, simply enter the following commands in the terminal:

```bash
$ cd site
$ kobweb run
```

and open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

---

## How to enable AI in Chrome

Chrome built-in AI is a preview feature, you need to use chrome version 127 or greater, now in [dev](https://www.google.com/chrome/dev/?extra=devchannel) or [canary](https://www.google.com/chrome/canary/) channel, [may release on stable chanel at Jul 17, 2024](https://chromestatus.com/roadmap).

After then, you should turn on these flags:
* [chrome://flags/#prompt-api-for-gemini-nano](chrome://flags/#prompt-api-for-gemini-nano): `Enabled`
* [chrome://flags/#optimization-guide-on-device-model](chrome://flags/#optimization-guide-on-device-model): `Enabled BypassPrefRequirement`
* [chrome://components/](chrome://components/): Click `Optimization Guide On Device Model` to download the model.