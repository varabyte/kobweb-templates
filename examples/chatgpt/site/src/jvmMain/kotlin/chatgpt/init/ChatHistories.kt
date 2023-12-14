package chatgpt.init

import apis.openai.endpoints.chat.Message
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext

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
fun initChatHistories(ctx: InitApiContext) {
    ctx.data.add(ChatHistories())
}
