package ${package}.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.TextInput
import ${package}.components.layouts.PageLayout
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    PageLayout("Welcome to Kobweb!") {
        Text("Please enter your name")
        var name by remember { mutableStateOf("") }
        TextInput(name, onTextChanged = { name = it }, Modifier.margin(top = 0.5.cssRem))
        P()
        Text("Hello ${r'${name.takeIf { it.isNotBlank() }'} ?: "World"}!")
    }
}
