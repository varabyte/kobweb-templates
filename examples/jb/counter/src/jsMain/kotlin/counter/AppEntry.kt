package counter

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    KobwebApp {
        content()
    }
}