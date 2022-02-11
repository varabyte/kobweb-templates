package ${package}.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.toCssColor
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.components.text.Text
import com.varabyte.kobweb.silk.theme.SilkTheme
import org.jetbrains.compose.web.css.*

val FooterStyle = ComponentStyle.base("footer") {
    Modifier
        .margin(top = 2.cssRem)
        .borderTop(1.px, LineStyle.Solid, SilkTheme.palettes[colorMode].border.toCssColor())
        .padding(topBottom = 1.cssRem, leftRight = 4.cssRem)
        .alignSelf(AlignSelf.Center)
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(FooterStyle.toModifier().then(modifier)) {
        Text("Made with ")
        Link("https://github.com/varabyte/kobweb", "Kobweb")
    }
}