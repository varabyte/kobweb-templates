package imageprocessor.util.processors

import imageprocessor.util.*

class BlurProcessor(val radius: Int) : ImageProcessorAdapter() {
    private lateinit var originalImageWithAlphaNormalized: Image

    override fun preProcess(image: MutableImage) {
        originalImageWithAlphaNormalized = image.copy().apply {
            pixels.forEachIndexed { index, pixel -> pixels[index] = pixel.alphaNormalized() }
        }
    }

    override fun Context.convertPixel(pixel: Pixel): Pixel {
        var sumR = 0
        var sumG = 0
        var sumB = 0
        var count = 0

        // Calculate the sum of the color values of the neighboring pixels, weighted by their alpha values
        for (dy in -radius..radius) {
            for (dx in -radius..radius) {
                val nx = x + dx
                val ny = y + dy

                // Check if the neighbor is within the image boundaries
                if (nx in 0 until image.w && ny in 0 until image.h) {
                    val neighborPixel = originalImageWithAlphaNormalized[nx, ny]

                    sumR += neighborPixel.r
                    sumG += neighborPixel.g
                    sumB += neighborPixel.b
                    count++
                }
            }
        }

        // Calculate the average color value
        val avgR = sumR / count
        val avgG = sumG / count
        val avgB = sumB / count

        // Return the new blurred pixel with the original alpha
        return pixel.copy(r = avgR, g = avgG, b = avgB)
    }
}
