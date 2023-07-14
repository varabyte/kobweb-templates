package chat.chat.pages

import androidx.compose.runtime.*
import chat.auth.components.sections.LoggedOutMessage
import chat.auth.model.auth.LoginState
import chat.chat.model.Message
import chat.core.G
import chat.core.components.layouts.PageLayout
import chat.core.components.sections.CenteredColumnContent
import chat.core.components.widgets.TextButton
import chat.core.components.widgets.TextInput
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.streams.ApiStream
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Text

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

        val messages = remember { mutableStateListOf<Message>() }
        val chatStream = remember { ApiStream("chat") }
        LaunchedEffect(Unit) {
            chatStream.connect { text ->
                messages.add(Json.decodeFromString<Message>(text))
            }
        }

        CenteredColumnContent {
            Column(
                ChatBoxStyle.toModifier().height(80.percent).width(G.Ui.Width.Large).fontSize(G.Ui.Text.MediumSmall)
            ) {
                messages.forEach { entry ->
                    Text(entry.toChatLine())
                    Br()
                }
            }
            Box(Modifier.width(G.Ui.Width.Large).height(60.px)) {
                var message by remember { mutableStateOf("") }

                fun sendMessage() {
                    val messageCopy = Message(account.username, message.trim())
                    messages.add(messageCopy)
                    message = ""
                    chatStream.send(Json.encodeToString<Message>(messageCopy))
                }
                TextInput(
                    message,
                    Modifier.width(70.percent).align(Alignment.BottomStart),
                    ref = { it.focus() },
                    onCommit = ::sendMessage
                ) { message = it }
                TextButton(
                    "Send",
                    Modifier.width(20.percent).align(Alignment.BottomEnd),
                    enabled = message.isNotBlank(),
                    onClick = ::sendMessage
                )
            }
        }
    }
}
