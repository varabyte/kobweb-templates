package chromeai.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.document
import chromeai.components.Colors
import chromeai.components.sections.Navbar
import org.jetbrains.compose.web.css.*

val LayoutStyle = CssStyle.base {
    Modifier.display(DisplayStyle.Flex)
        .flexDirection(FlexDirection.Column)
        .borderRadius(0.5.cssRem)
        .fillMaxWidth()
        .minHeight(100.vh)
        .boxShadow(
            BoxShadow.of(
                offsetX = 0.px,
                offsetY = 10.px,
                blurRadius = 15.px,
                spreadRadius = (-3).px,
                color = rgba(0, 0, 0, 0.1f)
            ),
            BoxShadow.of(
                offsetX = 0.px,
                offsetY = 4.px,
                blurRadius = 6.px,
                spreadRadius = (-2).px,
                color = rgba(0, 0, 0, 0.05f)
            )
        )
        .backgroundColor(Colors.BACKGROUND)
        .color(Colors.FOREGROUND)
}

@Composable
fun PageLayout(title: String, content: @Composable () -> Unit) {
    LaunchedEffect(title) {
        document.title = title
    }

    Column(
        modifier = LayoutStyle.toModifier()
    ) {
        Navbar()
        content()
    }
}