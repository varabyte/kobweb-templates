package imageprocessor.site.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.overlay.Overlay
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

val ModalStyle by ComponentStyle.base {
    Modifier
        .minWidth(300.px)
        .maxWidth(500.px)
        .backgroundColor(colorMode.toPalette().background)
        .margin(top = 6.vh)
        .padding(20.px)
        .gap(10.px)
        .borderRadius(2.percent)
}

val ModalContentStyle by ComponentStyle {
    base {
        Modifier
            .fillMaxWidth()
            .gap(10.px)
            .padding(5.px) // Avoid outlines clipping against the side / add space between buttons and scrollbar
            .maxHeight(60.vh)
            .overflow { y(Overflow.Auto) }
    }
}

val ModalButtonRowStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().margin(top = 1.cssRem).gap(1.cssRem) }
    cssRule(" *") { Modifier.flexGrow(1) }
}

@Composable
fun Modal(
    bottomRow: (@Composable RowScope.() -> Unit)? = null,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    Overlay {
        Column(ModalStyle.toModifier()) {
            content?.let { content ->
                Box(ModalContentStyle.toModifier()) {
                    content()
                }
            }
            if (bottomRow != null) {
                Row(ModalButtonRowStyle.toModifier()) {
                    bottomRow()
                }
            }
        }
    }
}
