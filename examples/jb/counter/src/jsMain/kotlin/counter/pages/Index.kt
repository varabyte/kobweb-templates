package counter.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

// See also: https://github.com/JetBrains/compose-jb/tree/master/tutorials/Web/Getting_Started
@Page
@Composable
fun MainPage() {
    var count by remember { mutableStateOf(0) }
    Div({ style { padding(25.px) } }) {
        Button(attrs = {
            onClick { count -= 1 }
        }) {
            Text("-")
        }

        Span({ style { padding(15.px) } }) {
            Text("$count")
        }

        Button(attrs = {
            onClick { count += 1 }
        }) {
            Text("+")
        }
    }
}