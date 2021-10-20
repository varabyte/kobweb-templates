package ${package}

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.height
import com.varabyte.kobweb.compose.ui.width
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.theme.SilkTheme
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        SilkTheme {
            Surface(Modifier.width(100.vw).height(100.vh)) {
                content()
            }
        }
    }
}