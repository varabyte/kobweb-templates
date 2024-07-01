package chromeai.model

import web.streams.ReadableStreamDefaultReader

sealed interface Message {
    data class User(val text: String) : Message
    data class AI(val stream: ReadableStreamDefaultReader<String>) : Message
}