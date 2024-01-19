package imageprocessor.util.processors

import imageprocessor.util.ImageProcessorAdapter
import imageprocessor.util.Pixel

class InvertProcessor : ImageProcessorAdapter() {
    override fun Context.convertPixel(pixel: Pixel): Pixel {
        return pixel.copy(r = 255 - pixel.r, g = 255 - pixel.g, b = 255 - pixel.b)
    }
}
