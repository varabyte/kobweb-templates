package chatgpt.init

import apis.openai.endpoints.chat.ApiKeyInterceptor
import apis.openai.endpoints.chat.Message
import chatgpt.serialization.JsonSerializer
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@InitApi
fun initOkHttp(ctx: InitApiContext) {
    val apiKey = String(ApiKeyInterceptor::class.java.classLoader.getResourceAsStream("openaikey.txt")!!.readAllBytes())
    ctx.data.add(
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    )
}
