package chat.chat.model

import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext

@InitApi
fun initAccounts(ctx: InitApiContext) {
    ctx.data.add(Messages())
}

class Messages {
    val list = mutableListOf<MessageEntry>()
}