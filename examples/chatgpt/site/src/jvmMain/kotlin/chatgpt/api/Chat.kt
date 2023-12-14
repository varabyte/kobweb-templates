package chatgpt.api

import chatgpt.model.ChatHistories
import chatgpt.model.MessageRequest
import chatgpt.model.MessageResponse
import apis.openai.endpoints.chat.Message
import apis.openai.endpoints.chat.sendMessageToChatGpt
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

@Api
suspend fun chat(ctx: ApiContext) {
    if (ctx.req.method != HttpMethod.POST) return

    val json = ctx.data.getValue<Json>()
    val httpClient = ctx.data.getValue<OkHttpClient>()
    val histories = ctx.data.getValue<ChatHistories>()

    val messageRequest = ctx.req.body
        ?.let { String(it) }
        ?.let { json.decodeFromString<MessageRequest>(it) }
        ?: return

    val gptResponse =
        sendMessageToChatGpt(json, httpClient, messageRequest.text, histories.getFor(messageRequest.chatId))

    val gptText = if (gptResponse != null && gptResponse.choices.isNotEmpty()) {
        gptResponse.choices.first().message.content
            .also { content ->
                histories.addMessage(messageRequest.chatId, Message("user", messageRequest.text))
                histories.addMessage(messageRequest.chatId, Message("assistant", content))
            }
    } else {
        "(Error retrieving text from ChatGPT. Please try again later.)"
    }

    ctx.res.body = json.encodeToString(MessageResponse(gptText)).encodeToByteArray()
}
