package ${package}.pages

<#macro worker_imports>
  <#if useWorker?boolean>
import com.varabyte.kobweb.worker.rememberWorker
import ${package}.worker.EchoWorker
  </#if>
</#macro>
<#macro worker>
  <#if useWorker?boolean>
    val worker = rememberWorker { EchoWorker { output -> console.log("Echoed: $output") } }
    LaunchedEffect(Unit) {
        worker.postInput("Hello, worker!")
    }

  </#if>
</#macro>
<#if useSilk?boolean>
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Text
<@worker_imports/>

@Page
@Composable
fun HomePage() {
    <@worker/>
    // TODO: Replace the following with your own content
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("THIS PAGE INTENTIONALLY LEFT BLANK")
    }
}
<#else>
import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
<@worker_imports/>

@Page
@Composable
fun HomePage() {
    <@worker/>
    // TODO: Replace the following with your own content
    Div(attrs = {
        style {
            display(DisplayStyle.Flex)
            width(100.percent)
            minHeight(100.vh)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
        }
    }) {
       Text("THIS PAGE INTENTIONALLY LEFT BLANK")
    }
}
</#if>
