package chat.chat.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.*
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import chat.auth.components.sections.LoggedOutMessage
import chat.auth.model.auth.LoginState
import chat.chat.model.FetchRequest
import chat.chat.model.FetchResponse
import chat.chat.model.Message
import chat.chat.model.MessageEntry
import chat.core.G
import chat.core.components.layouts.PageLayout
import chat.core.components.sections.CenteredColumnContent
import chat.core.components.widgets.TextButton
import chat.core.components.widgets.TextInput
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

val ChatBoxStyle by ComponentStyle.base {
    Modifier
        .padding(5.px)
        .borderRadius(5.px)
        .borderStyle(LineStyle.Solid)
        .overflowY(Overflow.Auto)
}

private fun Message.toChatLine() = "${this.username}: ${this.text}"

@Page
@Composable
fun ChatPage() {
    PageLayout("Chat") {
        val account = (LoginState.current as? LoginState.LoggedIn)?.account ?: run {
            LoggedOutMessage()
            return@PageLayout
        }

        var messageEntries by remember { mutableStateOf(listOf<MessageEntry>()) }
        var localMessage by remember { mutableStateOf<Message?>(null) }
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            while (true) {
                val request = FetchRequest(messageEntries.lastOrNull()?.id)
                messageEntries = messageEntries + window.api.get("/chat/fetchmessages?request=${Json.encodeToString(FetchRequest.serializer(), request)}")
                    .decodeToString().let { Json.decodeFromString<FetchResponse>(it) }.messages
                localMessage = null
                delay(1000)
            }
        }

        CenteredColumnContent {
            Column(ChatBoxStyle.toModifier().height(80.percent).width(G.Ui.Width.Large).fontSize(G.Ui.Text.MediumSmall)) {
                messageEntries.forEach { entry ->
                    val message = entry.message
                    Text(message.toChatLine())
                    Br()
                }
                localMessage?.let { localMessage ->
                    Text(localMessage.toChatLine())
                }
            }
            Box(Modifier.width(G.Ui.Width.Large).height(60.px)) {
                var message by remember { mutableStateOf("") }

                fun sendMessage() {
                    val messageCopy = Message(account.username, message.trim())
                    localMessage = messageCopy
                    message = ""
                    coroutineScope.launch {
                        window.api.post("/chat/sendmessage",
                            body = Json
                                .encodeToString(Message.serializer(), messageCopy)
                                .encodeToByteArray()
                        )
                    }
                }
                TextInput(message, Modifier.width(70.percent).align(Alignment.BottomStart), ref = { it.focus() }, onCommit = ::sendMessage) { message = it }
                TextButton("Send", Modifier.width(20.percent).align(Alignment.BottomEnd), enabled = message.isNotBlank(), onClick = ::sendMessage)
            }
        }
    }
}