package chromeai.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import js.promise.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Span
import web.streams.ReadableStreamDefaultReader
import web.streams.ReadableStreamReadDoneResult
import web.streams.ReadableStreamReadValueResult
import kotlin.js.Date


@Composable
fun BotResponse(readableStream: ReadableStreamDefaultReader<String>) {
    val time = Date().toLocaleTimeString("en-US", options = dateLocaleOptions {
        hour = "2-digit"
        minute = "2-digit"
    })

    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    scope.launch {
        while (true) {
            when (val result = readableStream.read().await()) {
                is ReadableStreamReadDoneResult -> break
                is ReadableStreamReadValueResult -> {
                    text = result.value
                }
            }
        }
    }
    SimpleGrid(
        numColumns = numColumns(1), modifier = Modifier.gap(8.px).maxWidth(36.cssRem)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.px), verticalAlignment = Alignment.CenterVertically
        ) {
            Span(
                attrs = Modifier.display(DisplayStyle.Flex).flexShrink(0).overflow(Overflow.Hidden)
                    .borderRadius(9999.px).size(32.px).toAttrs()
            ) {
                Image(
                    src = "/ai.webp", alt = "AI", modifier = Modifier.aspectRatio(1f).fillMaxSize()
                )
            }
            SpanText(
                "AI", modifier = Modifier.fontWeight(FontWeight.Medium)
            )
            SpanText(
                time, modifier = Modifier.fontWeight(FontWeight.Medium)
            )
        }
        Column(
            modifier = Modifier.maxWidth(36.cssRem).borderRadius(0.5.cssRem).padding(12.px).backgroundColor(Color.white)
        ) {
            SpanText(text)
        }
    }
}