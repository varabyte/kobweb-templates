package todo

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import org.jetbrains.compose.web.css.*

val BORDER_COLOR = Color.rgb(0xea, 0xea, 0xea)

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) = ctx.stylesheet.apply {
    registerStyleBase("html, body") { Modifier.fillMaxHeight() }
    registerStyleBase("body") {
        Modifier.fontFamily(
            "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Oxygen", "Ubuntu",
            "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "sans-serif"
        )
    }

    registerStyleBase("footer") {
        Modifier
            .width(100.percent)
            .height(100.px)
            .fontSize(1.5.cssRem)
            .borderTop(1.px, LineStyle.Solid, BORDER_COLOR)
            .display(DisplayStyle.Flex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
    }
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        Surface(Modifier.fillMaxHeight()) {
            content()
        }
    }
}
