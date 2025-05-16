package chat.core.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import chat.core.components.sections.NavHeader
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.data.addIfAbsent
import com.varabyte.kobweb.core.data.getValue
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobwebx.markdown.markdown
import kotlinx.browser.document
import org.jetbrains.compose.web.css.px

@InitRoute
fun initMarkdownLayout(ctx: InitRouteContext) {
    ctx.markdown!!.frontMatter["title"]?.singleOrNull()?.let { title ->
        ctx.data.add(PageLayoutData(title))
    }
}

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownLayout(content: @Composable () -> Unit) {
    content()
}
