package chatgpt.api

import apis.openai.endpoints.chat.sendMessageToChatGpt
import chatgpt.init.ChatHistories
import chatgpt.model.MessageChunkResponse
import chatgpt.model.MessageRequest
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.stream.ApiStream
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

val chat = object : ApiStream() {
    override suspend fun onTextReceived(ctx: TextReceivedContext) {
        val json = ctx.data.getValue<Json>()
        val httpClient = ctx.data.getValue<OkHttpClient>()
        val histories = ctx.data.getValue<ChatHistories>()

        val messageRequest = json.decodeFromString<MessageRequest>(ctx.text)

        sendMessageToChatGpt(
            json,
            httpClient,
            messageRequest.text,
            histories.getFor(messageRequest.chatId),
        ).collect { chunk ->
            chunk.choices.firstOrNull()?.delta?.content?.let { content ->
                ctx.stream.send(json.encodeToString(MessageChunkResponse(content)))
            } ?: run {
                ctx.stream.disconnect()
            }
        }
    }
}
