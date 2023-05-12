package responsive.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.*
import responsive.components.layouts.PageLayout

@Page
@Composable
fun ProfilePage() {
    PageLayout {
        H2 { Text("Profile") }
        Text("This is a placeholder page as this is only a demo project.")
    }
}