package chromeai.pages

import androidx.compose.runtime.*
import chromeai.components.widgets.SendButton
import com.github.dead8309.chrome.ai.TextSession
import com.github.dead8309.chrome.ai.ai
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import js.promise.await
import kotlinx.browser.window
import kotlinx.coroutines.launch
import chromeai.components.Colors
import chromeai.components.layouts.PageLayout
import chromeai.components.widgets.BotResponse
import chromeai.components.widgets.PromptInput
import chromeai.components.widgets.UserMessage
import chromeai.model.Message
import org.jetbrains.compose.web.css.*

@Page
@Composable
fun HomePage() {
    val messages = remember { mutableStateListOf<Message>() }
    var prompt by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val model = window.ai
    var session: TextSession? = null

    LaunchedEffect(Unit) {
        session = model.createTextSession().await()
    }

    fun onPromptSubmit() {
        messages.add(Message.User(prompt))
        scope.launch {
            if (session == null) {
                console.error("Session is nor ready yet")
                return@launch
            }
            val incomingStream = session!!.promptStreaming(prompt).getReader()
            messages.add(Message.AI(incomingStream))
            // reset prompt
            prompt = ""
        }
    }

    PageLayout("Chrome AI") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .flex(1)
                .overflow(
                    overflowY = Overflow.Auto,
                    overflowX = Overflow.Hidden
                )
                .backgroundColor(Colors.SECONDARY)
                .padding(16.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SimpleGrid(
                numColumns = numColumns(1),
                modifier = ChatGridStyle.toModifier()
            ) {
                messages.forEach {
                    when (it) {
                        is Message.User -> UserMessage(it.text)
                        is Message.AI -> BotResponse(it.stream)
                    }
                }
            }
        }

        Box(modifier = PromptContainerStyle.toModifier()) {
            Row(
                modifier = PromptRowStyle.toModifier(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.px),
            ) {
                PromptInput(
                    value = prompt,
                    onValueChange = { prompt = it },
                )
                SendButton(onClick = { onPromptSubmit() })
            }
        }
    }
}

val ChatGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .columnGap(16.px)
            .padding(leftRight = 8.px)
    }
    Breakpoint.MD {
        Modifier.padding(leftRight = 20.vw)
    }
}

val PromptContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .backgroundColor(Color.white)
        .position(Position.Sticky)
        .bottom(0.px)
        .padding(topBottom = 12.px)
        .borderTop {
            width(1.px)
            style(lineStyle = LineStyle.Solid)
            color(Colors.BORDER)
        }
}

val PromptRowStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(leftRight = 8.px)
    }
    Breakpoint.MD {
        Modifier
            .padding(leftRight = 20.vw)
    }
}