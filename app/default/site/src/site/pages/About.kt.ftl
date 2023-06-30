package ${package}.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import ${package}.components.layouts.PageLayout
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun AboutPage() {
    PageLayout("ABOUT") {
        Text("This is a skeleton app used to showcase a basic site made using Kobweb")
        P()
        Link("/", "Go Home")
    }
}
