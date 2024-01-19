@file:UseSerializers(ImageDataSerializer::class)
@file:Suppress("UnsafeCastFromDynamic") // Necessary for working with Uint8ClampedArray, otherwise values get truncated

package imageprocessor.worker

import com.varabyte.kobweb.worker.WorkerFactory
import com.varabyte.kobweb.worker.WorkerStrategy
import com.varabyte.kobwebx.worker.kotlinx.serialization.util.createIOSerializer
import imageprocessor.util.ImageProcessor
import imageprocessor.util.MutableImage
import imageprocessor.util.processors.*
import imageprocessor.util.toUint8ClampedArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.ImageData

@Serializable
@SerialName("ImageData")
private class ImageDataSurrogate(val w: Int, val h: Int, val data: IntArray)

private object ImageDataSerializer : KSerializer<ImageData> {
    private val surrogateSerializer = ImageDataSurrogate.serializer()
    override val descriptor = surrogateSerializer.descriptor

    override fun serialize(encoder: Encoder, value: ImageData) {
        val surrogate =
            ImageDataSurrogate(value.width, value.height, IntArray(value.data.length) { i -> value.data[i].toInt() })

        encoder.encodeSerializableValue(surrogateSerializer, surrogate)
    }

    override fun deserialize(decoder: Decoder): ImageData {
        val surrogate = decoder.decodeSerializableValue(surrogateSerializer)

        return ImageData(surrogate.w, surrogate.h).apply {
            surrogate.data.forEachIndexed { index, byte -> data[index] = byte.asDynamic() }
        }
    }
}

@Serializable
class ProcessImageRequest(
    val imageData: ImageData,
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

@Serializable
sealed class ProcessImageResponse {
    @Serializable
    class Finished(val imageData: ImageData) : ProcessImageResponse()

    @Serializable
    class Error(val message: String) : ProcessImageResponse()
}

internal class ImageProcessorWorkerFactory : WorkerFactory<ProcessImageRequest, ProcessImageResponse> {
    override fun createIOSerializer() = Json.createIOSerializer<ProcessImageRequest, ProcessImageResponse>()

    override fun createStrategy(postOutput: (output: ProcessImageResponse) -> Unit) =
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
                val image = MutableImage(input.imageData.width, input.imageData.height, input.imageData.data)

                processors.forEach { it.process(image) }
                postOutput(
                    ProcessImageResponse.Finished(
                        ImageData(image.toUint8ClampedArray(), image.w, image.h)
                    )
                )
            } catch (e: Exception) {
                postOutput(ProcessImageResponse.Error(e.message ?: "Unknown error"))
            }
        }
}
