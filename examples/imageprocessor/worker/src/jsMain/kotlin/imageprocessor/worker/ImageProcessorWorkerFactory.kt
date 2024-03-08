@file:Suppress("UnsafeCastFromDynamic") // Necessary for working with Uint8ClampedArray, otherwise values get truncated

package imageprocessor.worker

import com.varabyte.kobweb.worker.OutputDispatcher
import com.varabyte.kobweb.worker.Transferables
import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import com.varabyte.kobwebx.worker.kotlinx.serialization.util.createIOSerializer
import imageprocessor.util.ImageProcessor
import imageprocessor.util.Image
import imageprocessor.util.MutableImage
import imageprocessor.util.processors.*
import imageprocessor.util.toUint8ClampedArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.w3c.dom.ImageData

@Serializable
class ProcessImageRequest(
    val commands: List<ImageProcessorCommand>
)

@Suppress("CanSealedSubClassBeObject") // Using classes as we may add parameters in the future
@Serializable
sealed class ImageProcessorCommand {
    @Serializable
    class Blur(val radius: Int) : ImageProcessorCommand()

    @Serializable
    class Grayscale : ImageProcessorCommand()

    @Serializable
    class Invert : ImageProcessorCommand()

    @Serializable
    class Flip(val x: Boolean = false, val y: Boolean = false) : ImageProcessorCommand()
}

@Suppress("CanSealedSubClassBeObject") // Using classes as we may add parameters in the future
@Serializable
sealed class ProcessImageResponse {
    @Serializable
    class Finished : ProcessImageResponse()

    @Serializable
    class Error(val message: String) : ProcessImageResponse()
}

private fun Image.toImageData() = ImageData(this.toUint8ClampedArray(), w, h)

internal class ImageProcessorWorkerFactory : WorkerFactory<ProcessImageRequest, ProcessImageResponse> {
    override fun createIOSerializer() = Json.createIOSerializer<ProcessImageRequest, ProcessImageResponse>()

    override fun createStrategy(postOutput: OutputDispatcher<ProcessImageResponse>)=
        WorkerStrategy<ProcessImageRequest> { input ->
            val processors = mutableListOf<ImageProcessor>()
            for (command in input.commands) {
                when (command) {
                    is ImageProcessorCommand.Blur -> processors.add(BlurProcessor(command.radius))
                    is ImageProcessorCommand.Grayscale -> processors.add(GrayscaleProcessor())
                    is ImageProcessorCommand.Invert -> processors.add(InvertProcessor())
                    is ImageProcessorCommand.Flip -> {
                        if (command.x) processors.add(FlipXProcessor())
                        if (command.y) processors.add(FlipYProcessor())
                    }
                }
            }

            try {
                val imageData = transferables.getImageData("imageData")!!
                val image = MutableImage(imageData.width, imageData.height, imageData.data)

                processors.forEach { it.process(image) }
                postOutput(
                    ProcessImageResponse.Finished(),
                    Transferables { add("imageData", image.toImageData()) }
                )
            } catch (e: Exception) {
                postOutput(ProcessImageResponse.Error(e.message ?: "Unknown error (${e::class.simpleName})"))
            }
        }
}
