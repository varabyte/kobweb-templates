package imageprocessor.util.processors

import imageprocessor.util.ImageProcessorAdapter
import imageprocessor.util.Pixel

class GrayscaleProcessor : ImageProcessorAdapter() {
    override fun Context.convertPixel(pixel: Pixel): Pixel {
        val avg = (pixel.r + pixel.g + pixel.b) / 3
        return pixel.copy(r = avg, g = avg, b = avg)
    }
}
