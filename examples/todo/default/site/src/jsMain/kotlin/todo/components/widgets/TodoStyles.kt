package todo.components.widgets

import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.AlignItems
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.selectors.placeholderShown
import org.jetbrains.compose.web.css.*
import todo.BORDER_COLOR

private val INTERACT_COLOR = Color.rgb(0x00, 0x70, 0xf3)

/** Common styles for all todo widgets */
val TodoStyle = CssStyle.base {
    Modifier
        .width(85.percent)
        .height(5.cssRem)
        .border(1.px, LineStyle.Solid, BORDER_COLOR)
        .borderRadius(10.px)
        .transition(Transition.group(listOf("color", "border-color"), 0.15.s, TransitionTimingFunction.Ease))
        .textDecorationLine(TextDecorationLine.None)
}

/** Styles for the bordered, outer container (the form component has an inner and outer layer) */
val TodoContainerStyle = CssStyle.base {
    Modifier
        .margin(0.5.cssRem)
        .border(1.px, LineStyle.Solid, BORDER_COLOR)
        .display(DisplayStyle.Flex)
        .textAlign(TextAlign.Left)
        .alignItems(AlignItems.Center)
}

/** Styles for the text parts of todo widgets */
val TodoTextStyle = CssStyle.base {
    Modifier
        .padding(1.5.cssRem)
        .fontSize(1.25.cssRem)
        // We use "A" tags for accessibility, but we want our colors to come from our container
        .color(Color("inherit"))
}

/** Styles for the input element which handles user input */
val TodoInputStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.Transparent)
            .border(0.px)
    }

    placeholderShown {
        Modifier.fontStyle(FontStyle.Italic)
    }
}

/** Styles for mouse interaction with todo widgets */
val TodoClickableStyle = CssStyle {
    hover {
        Modifier
            .color(INTERACT_COLOR)
            .cursor(Cursor.Pointer)
            .border { color(INTERACT_COLOR) }
            .textDecorationLine(TextDecorationLine.LineThrough)
    }
}
