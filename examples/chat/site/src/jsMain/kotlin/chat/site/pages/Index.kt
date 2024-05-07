package chat.site.pages

import androidx.compose.runtime.Composable
import chat.auth.model.auth.LoginState
import chat.core.components.layouts.PageLayout
import chat.core.components.sections.CenteredColumnContent
import chat.core.components.widgets.TextButton
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext

@Page
@Composable
fun HomePage() {
    PageLayout("Kobweb Chat") {
        val ctx = rememberPageContext()
        CenteredColumnContent {
            if (LoginState.current is LoginState.LoggedIn) {
                TextButton("Go to Chat") { ctx.router.navigateTo("/chat") }
            }
            TextButton("Create Account") { ctx.router.navigateTo("/account/create") }
            TextButton("Login") { ctx.router.navigateTo("/account/login") }
        }
    }
}
