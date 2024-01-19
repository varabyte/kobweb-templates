package imageprocessor.site.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.dom.svg.Circle
import com.varabyte.kobweb.compose.dom.svg.SVGFillType
import com.varabyte.kobweb.compose.dom.svg.SVGStrokeLineCap
import com.varabyte.kobweb.compose.dom.svg.Svg
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.rotate
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.animation.Keyframes
import com.varabyte.kobweb.silk.components.animation.toAnimation
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.*

// Source: https://codepen.io/supah/pen/BjYLdW

val SpinnerRotate by Keyframes {
    100.percent { Modifier.rotate(360.deg) }
}

val SpinnerDash by Keyframes {
    0.percent {
        Modifier.styleModifier {
            property("stroke-dasharray", "1, 150")
            property("stroke-dashoffset", "0")
        }
    }

    50.percent {
        Modifier.styleModifier {
            property("stroke-dasharray", "90, 150")
            property("stroke-dashoffset", "-35")
        }
    }

    100.percent {
        Modifier.styleModifier {
            property("stroke-dasharray", "90, 150")
            property("stroke-dashoffset", "-124")
        }
    }
}


val SpinnerContainerStyle by ComponentStyle.base {
    // We put some margin inside the container so that the spinner SVG element doesn't keep messing with the width /
    // height of the current layout as it rotates. Even though the spinner itself is technically a circle, the SVG
    // element is a square, so as it rotates, its sharp corners keep escaping the current layout bounds. A parent
    // container prevents this from causing layout problems.
    Modifier.size(70.px).padding(10.px)
}

val SpinnerStyle by ComponentStyle.base {
    Modifier.animation(
        SpinnerRotate.toAnimation(
            colorMode,
            2.s,
            AnimationTimingFunction.Linear,
            iterationCount = AnimationIterationCount.Infinite
        )
    )
}

val SpinnerPathStyle by ComponentStyle.base {
    Modifier.animation(
        SpinnerDash.toAnimation(
            colorMode,
            1.5.s,
            AnimationTimingFunction.EaseIn,
            iterationCount = AnimationIterationCount.Infinite
        )
    )
}

@Composable
fun Spinner() {
    Box(SpinnerContainerStyle.toModifier()) {
        Svg(
            attrs = SpinnerStyle.toAttrs {
                viewBox(0, 0, 50, 50)
            }
        ) {
            Circle(attrs = SpinnerPathStyle.toModifier().toAttrs {
                classes("spinner-path")
                cx(25)
                cy(25)
                r(20)
                fill(SVGFillType.None)
                stroke(Colors.Black)
                strokeLineCap(SVGStrokeLineCap.Round)
                strokeWidth(5)
            })
        }
    }
}
