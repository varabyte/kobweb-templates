package chat.site.pages

import androidx.compose.runtime.Composable
import chat.auth.model.auth.LoginState
import chat.core.components.layouts.PageLayout
import chat.core.components.layouts.PageLayoutData
import chat.core.components.sections.CenteredColumnContent
import chat.core.components.widgets.TextButton
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Kobweb Chat"))
}

@Page
@Layout("chat.core.components.layouts.PageLayout")
@Composable
fun HomePage(ctx: PageContext) {
    CenteredColumnContent {
        if (LoginState.current is LoginState.LoggedIn) {
            TextButton("Go to Chat") { ctx.router.navigateTo("/chat") }
        }
        TextButton("Create Account") { ctx.router.navigateTo("/account/create") }
        TextButton("Login") { ctx.router.navigateTo("/account/login") }
    }
}
