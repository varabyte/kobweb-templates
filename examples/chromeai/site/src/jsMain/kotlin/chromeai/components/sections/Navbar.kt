package chromeai.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.functions.blur
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import chromeai.components.Colors
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Header

val HeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .position(Position.Sticky)
        .top(0.px)
        .zIndex(40)
        .borderBottom {
            width(1.px)
            style(lineStyle = LineStyle.Solid)
            color(Colors.BORDER)
        }
        .backdropFilter(blur(8.px))
        .padding(topBottom = 0.75.cssRem, leftRight = 1.cssRem)
}

val RowStyle = CssStyle {
    base { Modifier.margin(leftRight = 8.px) }
    Breakpoint.MD { Modifier.margin(leftRight = 32.px) }
}

@Composable
fun Navbar() {
    Header(HeaderStyle.toModifier().toAttrs()) {
        Row(
            modifier = RowStyle.toModifier(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy((0.75).cssRem)
        ) {
            Image(
                src = "/ai.webp",
                alt = "AI",
                height = 32,
                width = 32,
            )
            SpanText(
                modifier = Modifier
                    .fontSize(1.125.cssRem)
                    .lineHeight(1.75.cssRem)
                    .fontWeight(600),
                text = "Chrome AI"
            )
        }
    }
}