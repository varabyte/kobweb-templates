package chat.core.components.widgets

import androidx.compose.runtime.Composable
import chat.core.G
import com.varabyte.kobweb.compose.dom.ElementRefScope
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement

val TitledTextInputLabelStyle by ComponentStyle.base {
    Modifier
        .fontSize(G.Ui.Text.Small)
        .color(Colors.Grey)
}

val TitledTextInputStyle by ComponentStyle.base {
    Modifier
        .width(G.Ui.Width.Medium)
        .margin(bottom = 10.px)
}


/** A text input box with a descriptive label above it. */
@Composable
fun TitledTextInput(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    masked: Boolean = false,
    onCommit: () -> Unit = {},
    ref: ElementRefScope<HTMLInputElement>? = null,
) {
    Column {
        SpanText(title, TitledTextInputLabelStyle.toModifier())
        TextInput(text, onTextChanged, TitledTextInputStyle.toModifier(), password = masked, onCommit = onCommit, ref = ref)
    }
}
