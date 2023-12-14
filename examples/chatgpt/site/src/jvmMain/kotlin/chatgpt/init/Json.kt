package chatgpt.init

import chatgpt.serialization.JsonSerializer
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext

@InitApi
fun initJson(ctx: InitApiContext) {
    ctx.data.add(JsonSerializer)
}
