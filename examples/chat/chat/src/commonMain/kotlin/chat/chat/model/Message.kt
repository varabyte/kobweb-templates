package chat.chat.model

import kotlinx.serialization.Serializable

@Serializable
class Message(val username: String, val text: String)
