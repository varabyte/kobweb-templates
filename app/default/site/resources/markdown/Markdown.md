---
root: .components.layouts.PageLayout("MARKDOWN")
---

## Markdown Example

This page is generated from markdown.

Kobweb supports all Markdown features, including

```
Multi-line
code blocks
```

as well as `inline code` formatting.

Of course, links are supported. For example:

"Use Kobweb to create rich, dynamic web apps with ease, leveraging [Kotlin](https://kotlinlang.org/)
and [Compose HTML](https://github.com/JetBrains/compose-multiplatform#compose-html)."

You can also embed HTML code directly inside your page. Here, the following will create a badge that links to the
Varabyte Discord server:

<a href="https://discord.gg/5NZ2GKV5Cs">
  <img alt="Varabyte Discord" src="https://img.shields.io/discord/886036660767305799.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2" />
</a>

Alternately, you can use Kobweb's custom `{{{ code }}}` syntax to call into your project's Kotlin code. You can read
more about this feature [in Kobweb's README](https://github.com/varabyte/kobweb#kobweb-call).

In fact, the following may look like an ordinary link, but it is actually provided by Kotlin code:

{{{ .components.widgets.GoHomeLink }}}
