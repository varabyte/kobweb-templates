package chatgpt.model

import apis.openai.endpoints.chat.ApiKeyInterceptor
import apis.openai.endpoints.chat.Message
import chatgpt.serialization.JsonSerializer
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ChatHistories {
    private val _values = mutableMapOf<String, MutableList<Message>>()

    fun addMessage(chatId: String, message: Message) {
        _values.getOrPut(chatId) { mutableListOf() }.add(message)
    }

    fun clear(chatId: String) {
        _values.remove(chatId)
    }

    fun getFor(chatId: String): List<Message> {
        return _values[chatId] ?: emptyList()
    }
}

@InitApi
fun initApi(ctx: InitApiContext) {
    val apiKey = String(ApiKeyInterceptor::class.java.classLoader.getResourceAsStream("openaikey.txt")!!.readAllBytes())

    ctx.data.add(JsonSerializer)

    ctx.data.add(
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    )

    ctx.data.add(ChatHistories())
}
