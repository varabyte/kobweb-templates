package imageprocessor.util.processors

import imageprocessor.util.*

class FlipXProcessor : ImageProcessorAdapter() {
    private lateinit var originalImage: Image

    override fun preProcess(image: MutableImage) {
        originalImage = image.copy()
    }

    override fun Context.convertPixel(pixel: Pixel): Pixel {
        return originalImage[image.w - x - 1, y]
    }
}

class FlipYProcessor : ImageProcessorAdapter() {
    private lateinit var originalImage: Image

    override fun preProcess(image: MutableImage) {
        originalImage = image.copy()
    }

    override fun Context.convertPixel(pixel: Pixel): Pixel {
        return originalImage[x, image.h - y - 1]
    }
}
