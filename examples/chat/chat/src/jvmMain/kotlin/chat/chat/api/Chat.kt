package chat.chat.api

import com.varabyte.kobweb.api.stream.ApiStream

val chat = ApiStream { ctx ->
    ctx.stream.broadcast(ctx.text) { it != ctx.clientId }
}
