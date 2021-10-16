package ${package}

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.theme.SilkTheme
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        SilkTheme {
            Div({
                style {
                    height(100.vh)
                    width(100.vw)
                }
            }) {
                Surface {
                    content()
                }
            }
        }
    }
}