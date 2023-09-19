package ${package}.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.AlignSelf
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.SilkTheme
import com.varabyte.kobweb.silk.theme.colors.palette.border
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import org.jetbrains.compose.web.css.*

val FooterStyle by ComponentStyle.base {
    Modifier
        .margin(top = 2.cssRem)
        .borderTop(1.px, LineStyle.Solid, colorMode.toPalette().border)
        .padding(topBottom = 1.cssRem, leftRight = 4.cssRem)
        .alignSelf(AlignSelf.Center)
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(FooterStyle.toModifier().then(modifier)) {
        SpanText("Made with ")
        Link("https://github.com/varabyte/kobweb", "Kobweb")
    }
}
