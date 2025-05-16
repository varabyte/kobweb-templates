package opengl.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.SilkTheme
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
@Layout
fun PageLayout(content: @Composable BoxScope.() -> Unit) {
    Column(
        Modifier.fillMaxWidth().minHeight(100.vh),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                var colorMode by ColorMode.currentState
                Button(
                    onClick = { colorMode = colorMode.opposite },
                    Modifier.margin(10.px).padding(0.px).borderRadius(50.percent)
                ) {
                    if (colorMode.isLight) FaSun() else FaMoon()
                }
            }
            Box(Modifier.fillMaxSize()) {
                content()
            }
        }

        val borderColor = SilkTheme.palette.color
        Spacer()
        Box(
            Modifier
                .fillMaxWidth()
                .borderTop(1.px, LineStyle.Solid, borderColor)
                .fontSize(1.5.cssRem),
            Alignment.Center
        ) {
            // Use PreWrap to preserve trailing space in text
            Span(Modifier.margin(topBottom = 1.cssRem).whiteSpace(WhiteSpace.PreWrap).textAlign(TextAlign.Center).toAttrs()) {
                Text("This project is built using ")
                Link(
                    "https://github.com/varabyte/kobweb",
                    "Kobweb",
                )
                Text(", a full-stack Kotlin web framework.")
            }
        }
    }
}
