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
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement

val TitledTextInputLabelStyle = CssStyle.base {
    Modifier
        .fontSize(G.Ui.Text.Small)
        .color(Colors.Grey)
}

val TitledTextInputStyle = CssStyle.base {
    Modifier
        .width(G.Ui.Width.Medium)
        .margin(bottom = 10.px)
}


/** A text input box with a descriptive label above it. */
@Composable
fun TitledTextInput(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    masked: Boolean = false,
    onCommit: () -> Unit = {},
    ref: ElementRefScope<HTMLInputElement>? = null,
) {
    Column {
        SpanText(title, TitledTextInputLabelStyle.toModifier())
        TextInput(text, onTextChange, TitledTextInputStyle.toModifier(), password = masked, onCommit = onCommit, ref = ref)
    }
}
