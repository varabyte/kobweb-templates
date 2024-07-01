package chromeai.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toAttrs
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun PromptInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextArea(
        value = value,
        attrs = PromptTextAreaStyle.toAttrs {
            onInput { onValueChange(it.value) }
            placeholder("Type a message...")
        }
    )
}

val PromptTextAreaStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .minHeight(80.px)
        .display(DisplayStyle.Flex)
        .border { width(1.px) }
        .borderRadius(0.5.cssRem)
        .padding(
            left = 0.75.cssRem,
            right = 3.75.cssRem,
            top = 0.5.cssRem,
            bottom = 0.5.cssRem
        )
        .fontSize(0.875.cssRem)
        .lineHeight(1.25.cssRem)
        .resize(Resize.None)
}