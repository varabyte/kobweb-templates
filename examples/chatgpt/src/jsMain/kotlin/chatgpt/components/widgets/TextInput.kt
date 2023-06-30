package chat.core.components.widgets

import androidx.compose.runtime.*
import chatgpt.G
import com.varabyte.kobweb.compose.dom.ElementRefScope
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.asAttributesBuilder
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

val TextInputStyle by ComponentStyle.base {
    Modifier
        .fontSize(G.Ui.Text.Medium)
        .borderColor(Colors.Transparent)
        .boxShadow(0.px, 0.px, 5.px, 0.px, Colors.Black)
        .borderRadius(5.px)
}

/** A controlled text input box. */
@Composable
fun TextInput(
    text: String,
    modifier: Modifier = Modifier,
    ref: ((HTMLInputElement) -> Unit)? = null,
    onCommit: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    Input(
        InputType.Text,
        attrs = TextInputStyle.toModifier().then(modifier).toAttrs {
            if (ref != null) {
                this.ref { element ->
                    ref(element)
                    onDispose { }
                }
            }
            value(text)
            onInput { onValueChanged(it.value) }
            onKeyUp { evt ->
                if (evt.code == "Enter") {
                    evt.preventDefault()
                    onCommit()
                }
            }
        }
    )
}
