package ${package}

<#if useSilk?boolean>
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.AnimatedColorSurfaceVariant
import com.varabyte.kobweb.silk.components.layout.Surface
import org.jetbrains.compose.web.css.*

@InitSilk
fun updateTheme(ctx: InitSilkContext) {
    // Configure silk here
}

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        Surface(Modifier.minWidth(100.vw).minHeight(100.vh), variant = AnimatedColorSurfaceVariant) {
            content()
        }
    }
}
<#else>
import androidx.compose.runtime.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    KobwebApp {
        content()
    }
}
</#if>
