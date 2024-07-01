package chromeai.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import chromeai.components.Colors
import org.jetbrains.compose.web.css.*

@Composable
fun SendButton(
    onClick: () -> Unit,
) {
    Button(
        modifier = SendButtonStyle.toModifier(),
        onClick = { onClick() }
    ) {
        FaPaperPlane(
            size = IconSize.LG
        )
    }
}

val SendButtonStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Colors.PRIMARY)
            .color(Color.white)
            .display(DisplayStyle.LegacyInlineFlex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .borderRadius(12.px)
            .size(3.5.cssRem)
            .transition(
                Transition.of(
                    property = TransitionProperty.of("color,background-color,border-color,text-decoration-color,fill,stroke"),
                    duration = 300.ms,
                    timingFunction = TransitionTimingFunction.cubicBezier(0.4, 0.0, 0.2, 1.0)
                )
            )
    }
    hover {
        Modifier.backgroundColor(hsl(240, 5.9, 30))
    }
}