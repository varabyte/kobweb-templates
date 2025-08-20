package imageprocessor.site.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.browser.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.worker.Attachments
import imageprocessor.site.components.widgets.Modal
import imageprocessor.site.components.widgets.Spinner
import imageprocessor.worker.ImageProcessorCommand
import imageprocessor.worker.ImageProcessorWorker
import imageprocessor.worker.ProcessImageRequest
import imageprocessor.worker.ProcessImageResponse
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Canvas
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image

private val contextArgument = js("{ willReadFrequently: true }")
private fun HTMLCanvasElement.get2dContext() = getContext("2d", contextArgument) as CanvasRenderingContext2D
private fun CanvasRenderingContext2D.getImageData() = getImageData(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())

val ImageCanvasStyle = CssStyle.base {
    Modifier.border(1.px, LineStyle.Solid, Colors.Black).borderRadius(5.px).cursor(Cursor.Pointer)
}

@Page
@Composable
fun HomePage() {
    val image = remember { Image() }

    var canReset by remember { mutableStateOf(false) }
    var _canvas by remember { mutableStateOf<HTMLCanvasElement?>(null) }
    fun assertCanvas() = _canvas ?: error("Unexpected: Canvas not initialized yet")

    LaunchedEffect(_canvas) {
        while (true) {
            if (_canvas != null) {
                image.src = "/default.png"
                break
            }
            delay(100)
        }
    }

    image.onload = {
        image.style.display = "none"
        val canvas = assertCanvas()
        canvas.width = image.width
        canvas.height = image.height
        val ctx = canvas.get2dContext()
        ctx.drawImage(image, 0.0, 0.0)
        Unit
    }

    Column(
        Modifier.fillMaxSize().padding(5.cssRem).padding { bottom(0.px) }.gap(1.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val commandChoices = remember {
            mapOf(
                ImageProcessorCommand.Grayscale() to "Grayscale",
                ImageProcessorCommand.Blur(3) to "Blur",
                ImageProcessorCommand.Invert() to "Invert",
                ImageProcessorCommand.Flip(x = true) to "Flip ↔",
                ImageProcessorCommand.Flip(y = true) to "Flip ↕",
            )
        }
        val commands = remember { mutableStateListOf<ImageProcessorCommand>() }

        Canvas(attrs = ImageCanvasStyle
            .toModifier()
            .onClick {
                document.loadDataUrlFromDisk("image/*") { url ->
                    image.src = url
                    commands.clear()
                }
            }
            .toAttrs {
                ref {
                    _canvas = it; onDispose { }
                }
            })

        Tooltip(ElementTarget.PreviousSibling, "Click to change the current image")

        Row(Modifier.gap(2.cssRem)) {
            commandChoices.forEach { (command, name) ->
                Checkbox(
                    checked = commands.contains(command),
                    onCheckedChange = { checked ->
                        if (checked) {
                            commands.add(command)
                        } else {
                            commands.remove(command)
                        }
                    }) {
                    Text(name)
                }
            }
        }

        var activeWorker by remember { mutableStateOf<ImageProcessorWorker?>(null) }
        fun discardActiveWorker() {
            activeWorker?.terminate()
            activeWorker = null
        }

        Row(Modifier.gap(2.cssRem)) {
            Button(onClick = {
                val canvas = assertCanvas()
                val ctx = canvas.get2dContext()
                val imageData = ctx.getImageData()

                // Instead of using `rememberWorker`, we create a worker on demand when a button is pressed. This
                // lets us cancel working on the image if the user presses the "Cancel" button, at which point we can
                // discard it, and when a new request comes in, we can just create a new worker.
                val worker = ImageProcessorWorker { response ->
                    commands.clear()

                    if (response is ProcessImageResponse.Finished) {
                        val processedImageData = attachments.getImageData("imageData")!!
                        ctx.putImageData(processedImageData, 0.0, 0.0)
                    } else if (response is ProcessImageResponse.Error) {
                        console.error("Error: ${response.message}")
                    }

                    canReset = true
                    discardActiveWorker()
                }.also { activeWorker = it }

                worker.postInput(
                    ProcessImageRequest(commands),
                    Attachments { add("imageData", imageData) }
                )
            }, enabled = (_canvas != null && activeWorker == null && (commands.isNotEmpty()))) {
                Text("Process!")
            }

            Button(onClick = {
                val canvas = assertCanvas()
                val ctx = canvas.get2dContext()
                ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
                ctx.drawImage(image, 0.0, 0.0)
                canReset = false

                commands.clear()
            }, enabled = _canvas != null && canReset) {
                Text("Reset")
            }
        }

        if (activeWorker != null) {
            Modal(
                bottomRow = {
                    Button(onClick = {
                        discardActiveWorker()
                    }) {
                        Text("Cancel")
                    }
                }
            ) {
                Column(Modifier.fillMaxSize().fontSize(2.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
                    SpanText("Processing your image...")
                    Spinner()
                }
            }
        }

        Spacer()
        Footer {
            Span(Modifier.margin(topBottom = 1.cssRem).whiteSpace(WhiteSpace.PreWrap).textAlign(TextAlign.Center).toAttrs()) {
                SpanText("This project is built using ")
                Link(
                    "https://github.com/varabyte/kobweb",
                    "Kobweb",
                )
                SpanText(", a full-stack Kotlin web framework.")
            }
        }
    }
}
