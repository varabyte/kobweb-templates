package apis.openai.endpoints.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

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

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<Message>,
)

@Serializable
data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
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

// This naive implementation is only provided for this demo. In production, this logic may cause problems! For example,
// it will almost certainly fail if the message contains Chinese glyphs. OpenAI provides a library to calculate the
// tokens for you, but it is written in Python, so I'm just provided a very naive implementation here for the sake of
// the demo.
// See also: https://huggingface.co/docs/transformers/model_doc/gpt2#transformers.GPT2TokenizerFast
private fun approximateTokenCountOf(text: String): Int {
    return Regex("""\b\w+\b""").findAll(text).count()
}

suspend fun sendMessageToChatGpt(
    json: Json,
    httpClient: OkHttpClient,
    message: String,
    history: List<Message>
): ChatResponse? {
    var tokenLimit = 4096 - approximateTokenCountOf(message)
    val recentHistory =
        history.reversed().takeWhile {
            val tokenCount = approximateTokenCountOf(it.content)
            val keepGoing = tokenLimit >= tokenCount
            tokenLimit -= tokenCount
            keepGoing
        }
            .reversed()

    val request = Request.Builder()
        .url("${CHATGPT_BASE_URL}chat/completions")
        .post(
            json.encodeToString(
                ChatRequest(
                    model = "gpt-3.5-turbo",
                    recentHistory + Message(
                        role = "user",
                        content = message
                    )
                )
            ).toRequestBody(JSON_MEDIA_TYPE)
        ).build()

    return withContext(Dispatchers.IO) {
        val call = httpClient.newCall(request)
        val response = call.execute()

        response.body?.takeIf { response.code == 200 }?.let { body ->
            json.decodeFromString(body.string())
        }
    }
}
