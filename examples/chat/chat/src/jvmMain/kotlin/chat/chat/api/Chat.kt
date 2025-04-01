package chat.chat.api

import com.varabyte.kobweb.api.stream.ApiStream
import com.varabyte.kobweb.api.stream.broadcastExcluding

val chat = ApiStream { ctx ->
    ctx.stream.broadcastExcluding(ctx.text, ctx.stream.id)
}
