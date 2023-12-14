package chatgpt.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import chatgpt.G
import chatgpt.components.widgets.LoadingSpinner
import chatgpt.model.MessageChunkResponse
import chatgpt.model.MessageRequest
import chatgpt.serialization.JsonSerializer
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconStyle
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.streams.ApiStream
import com.varabyte.kobweb.streams.ApiStreamListener
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

private data class HistoryEntry(val request: String, val response: String?)

@Composable
private fun TextBubble(text: String, color: CSSColorValue, modifier: Modifier = Modifier) {
    // ChatGPT can return newlines in its responses, which we need to convert to <br> tags to render properly in HTML
    Box(Modifier.backgroundColor(color).padding(10.px).borderRadius(5.px).then(modifier)) {
        text.lines().forEach { line ->
            Text(line)
            Br()
        }
    }
}

@Composable
private fun ChatArea(
    entries: List<HistoryEntry>,
    onHistoryChanged: (HTMLElement) -> Unit,
    modifier: Modifier = Modifier
) {
    var historyElement by remember { mutableStateOf<HTMLElement?>(null) }
    Column(
        Modifier.width(500.px).gap(10.px).padding(leftRight = 20.px).then(modifier),
        ref = ref { historyElement = it }) {
        entries.forEach { entry ->
            Row(Modifier.align(Alignment.Start).gap(5.px)) {
                SpanText("\uD83D\uDDE3\uFE0F", Modifier.padding(top = 5.px)) // Person speaking icon
                TextBubble(entry.request, G.Ui.Color.Human)
            }

            entry.response?.let { response ->
                Row(Modifier.align(Alignment.Start).gap(5.px)) {
                    SpanText("\uD83E\uDD16", Modifier.padding(top = 5.px)) // Robot icon
                    TextBubble(response, G.Ui.Color.Bot)
                }
            } ?: run {
                Box(Modifier.align(Alignment.CenterHorizontally)) {
                    LoadingSpinner()
                }
            }
        }

        // We need to ping the callback but invoke later to give the DOM a chance to update first.
        window.setTimeout({ historyElement?.let { onHistoryChanged(it) } })
    }
}

@Composable
private fun MessageAndSendButton(
    coroutineScope: CoroutineScope,
    chatId: String,
    onRequest: (String) -> Unit,
    onChunk: (String) -> Unit,
) {
    var userText by remember { mutableStateOf("") }
    var isStreaming by remember { mutableStateOf(false) }

    fun sendMessage() {
        onRequest(userText)

        // Create a one-off api stream to handle a single back and forth. Every new message that gets sent will open a
        // new streaming connection
        coroutineScope.launch {
            val chunkStream = ApiStream("chat")
            chunkStream.connect(object : ApiStreamListener {
                override fun onConnected(ctx: ApiStreamListener.ConnectedContext) {
                    val messageRequest = MessageRequest(chatId, userText)
                    userText = ""
                    ctx.stream.send(JsonSerializer.encodeToString(messageRequest))
                    isStreaming = true
                }

                override fun onTextReceived(ctx: ApiStreamListener.TextReceivedContext) {
                    onChunk(JsonSerializer.decodeFromString<MessageChunkResponse>(ctx.text).text)
                }

                override fun onDisconnected(ctx: ApiStreamListener.DisconnectedContext) {
                    isStreaming = false
                }
            })
        }
    }

    Row(Modifier.fillMaxWidth(95.percent).height(40.px).gap(10.px).margin(20.px)) {
        TextInput(
            userText,
            { userText = it },
            Modifier.fillMaxSize(),
            enabled = chatId.isNotEmpty(),
            placeholder = "Send a message to ChatGPT",
            ref = ref {
                it.focus()
                it.placeholder = "Send a message to ChatGPT"
            }, onCommit = { sendMessage() }
        )
        Button(
            onClick = { sendMessage() },
            Modifier.fillMaxHeight(),
            enabled = chatId.isNotEmpty() && userText.isNotBlank() && !isStreaming
        ) {
            FaPaperPlane(style = IconStyle.FILLED)
        }
    }
}

@Page
@Composable
fun HomePage() {
    val coroutineScope = rememberCoroutineScope()
    var chatId by remember { mutableStateOf("") }

    coroutineScope.launch {
        chatId = window.api.get("id").decodeToString()
    }

    SimpleGrid(
        numColumns(1),
        Modifier
            .fillMaxWidth()
            .minWidth(500.px)
            .height(100.percent)
            .justifyItems(JustifyItems.Center)
            .gridTemplateRows {
                lineNames("title"); size(minContent)
                lineNames("content"); size(1.fr)
                lineNames("chat"); size(minContent)
            }
    ) {
        H1 {
            Text("ChatGPT Demo")
        }

        val historyEntries = remember { mutableStateListOf<HistoryEntry>() }
        ChatArea(
            historyEntries,
            onHistoryChanged = { historyElement ->
                historyElement.scrollTop = historyElement.scrollHeight.toDouble()
            },
            Modifier.minHeight(200.px).overflow { y(Overflow.Auto) }
        )

        if (chatId.isNotEmpty()) {
            MessageAndSendButton(
                coroutineScope,
                chatId,
                onRequest = { request ->
                    historyEntries.add(HistoryEntry(request, null))
                },
                onChunk = { chunk ->
                    // History entries are immutable, so remove the last one (which will only have user text
                    // in it) and create a new one with the bot response included as well.
                    Snapshot.withMutableSnapshot {
                        val last = historyEntries.removeLast()
                        historyEntries.add(last.copy(response = last.response.orEmpty() + chunk))
                    }
                }
            )
        } else {
            LoadingSpinner()
        }
    }
}
