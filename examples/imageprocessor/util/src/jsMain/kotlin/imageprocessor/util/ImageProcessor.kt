package imageprocessor.util

interface ImageProcessor {
    fun process(image: MutableImage)
}

abstract class ImageProcessorAdapter : ImageProcessor {
    interface Context {
        val image: Image
        val x: Int
        val y: Int
    }

    private class MutableContext(override val image: MutableImage) : Context {
        override var x: Int = 0
        override var y: Int = 0
    }

    protected open fun preProcess(image: MutableImage) {}

    protected abstract fun Context.convertPixel(pixel: Pixel): Pixel

    override fun process(image: MutableImage) {
        preProcess(image)

        val ctx = MutableContext(image)
        for (y in 0 until image.h) {
            for (x in 0 until image.w) {
                ctx.x = x
                ctx.y = y
                image[x, y] = ctx.convertPixel(image[x, y])
            }
        }
    }
}
