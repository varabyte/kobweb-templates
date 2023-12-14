package apis.openai.endpoints.chat

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources

const val CHATGPT_BASE_URL = "https://api.openai.com/v1/"
val JSON_MEDIA_TYPE = "application/json".toMediaType()

class ApiKeyInterceptor(val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()

        return chain.proceed(request)
    }
}

@Serializable
data class Message(
    val role: String,
    val content: String,
)

// https://platform.openai.com/docs/api-reference/chat/create
@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean? = null,
    // This is missing a bunch of properties for configuring the completion that I don't need. If you do, add them!
)

// https://platform.openai.com/docs/api-reference/chat/object
@Serializable
data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val systemFingerprint: String?,
    val choices: List<Choice>,
) {
    @Serializable
    data class Usage(
        val promptTokens: Long,
        val completionTokens: Long,
        val totalTokens: Long
    )

    @Serializable
    data class Choice(
        val message: Message,
        val finishReason: String,
        val index: Long,
    )
}

const val STREAM_TERMINATED_MESSAGE = "[DONE]"

// https://platform.openai.com/docs/api-reference/chat/streaming
@Serializable
data class ChatChunkResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val systemFingerprint: String?,
    val choices: List<Choice>
) {
    @Serializable
    data class Choice(
        val delta: Delta,
        val finishReason: String?,
        val index: Long,
    )

    @Serializable
    data class Delta(
        val content: String? = null,
    )
}

// This naive implementation is only provided for this demo. In production, this logic may cause problems! For example,
// it will almost certainly fail if the message contains Chinese glyphs. OpenAI provides a library to calculate the
// tokens for you, but it is written in Python, so I'm just provided a very naive implementation here for the sake of
// the demo.
// See also: https://huggingface.co/docs/transformers/model_doc/gpt2#transformers.GPT2TokenizerFast
private fun approximateTokenCountOf(text: String): Int {
    return Regex("""\b\w+\b""").findAll(text).count()
}

/**
 * Send a message to ChatGPT, which will respond with a flow containing streamed chunks of text.
 *
 * This allows the client to listen to the response as it comes in, similar to the ChatGPT site works.
 */
suspend fun sendMessageToChatGpt(
    json: Json,
    httpClient: OkHttpClient,
    message: String,
    history: List<Message>,
): Flow<ChatChunkResponse> {
    var tokenLimit = 4096 - approximateTokenCountOf(message)
    val recentHistory =
        history.reversed().takeWhile {
            val tokenCount = approximateTokenCountOf(it.content)
            val keepGoing = tokenLimit >= tokenCount
            tokenLimit -= tokenCount
            keepGoing
        }
            .reversed()

    val eventSourceFactory = EventSources.createFactory(httpClient)

    val request = Request.Builder()
        .url("${CHATGPT_BASE_URL}chat/completions")
        .post(
            json.encodeToString(
                ChatRequest(
                    model = "gpt-3.5-turbo",
                    recentHistory + Message(
                        role = "user",
                        content = message
                    ),
                    stream = true
                )
            ).toRequestBody(JSON_MEDIA_TYPE)
        ).build()

    return callbackFlow {
        eventSourceFactory.newEventSource(request, listener = object : EventSourceListener() {
            override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
                if (data == STREAM_TERMINATED_MESSAGE) {
                    close()
                    return
                }

                try {
                    trySendBlocking(json.decodeFromString(data))
                } catch (t: Throwable) {
                    eventSource.cancel()
                }
            }

            override fun onClosed(eventSource: EventSource) {
                close()
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                close(t)
            }
        })

        awaitClose()
    }
}
