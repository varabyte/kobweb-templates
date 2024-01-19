@file:Suppress("UnsafeCastFromDynamic") // Necessary for working with Uint8ClampedArray, otherwise values get truncated

package imageprocessor.util

import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.get
import org.khronos.webgl.set

data class Pixel(val r: Int, val g: Int, val b: Int, val a: Int = 255) {
    init {
        check(r in 0..255) { "Expected r to be in range [0, 255], got $r" }
        check(g in 0..255) { "Expected g to be in range [0, 255], got $g" }
        check(b in 0..255) { "Expected b to be in range [0, 255], got $b" }
        check(a in 0..255) { "Expected a to be in range [0, 255], got $a" }
    }

    fun alphaNormalized(): Pixel {
        if (this.a == 255) return this
        val r = this.r * this.a / 255
        val g = this.g * this.a / 255
        val b = this.b * this.a / 255
        return Pixel(r, g, b, 255)
    }
}

interface Image {
    val w: Int
    val h: Int
    val pixels: List<Pixel>

    fun copy(): Image = MutableImage(w, h, pixels)
}

operator fun Image.get(x: Int, y: Int) = pixels[y * w + x]

private fun Uint8ClampedArray.toPixels(): List<Pixel> {
    check(length % 4 == 0) { "Expected a multiple of 4 bytes, got $length" }
    return List(length / 4) { i ->
        Pixel(
            this[i * 4 + 0].asDynamic(),
            this[i * 4 + 1].asDynamic(),
            this[i * 4 + 2].asDynamic(),
            this[i * 4 + 3].asDynamic(),
        )
    }
}

private fun List<Pixel>.toUint8ClampedArray(w: Int, h: Int): Uint8ClampedArray {
    val pixels = this
    return Uint8ClampedArray(w * h * 4).apply {
        var byteIndex = 0
        for (pixel in pixels) {
            this[byteIndex++] = pixel.r.asDynamic()
            this[byteIndex++] = pixel.g.asDynamic()
            this[byteIndex++] = pixel.b.asDynamic()
            this[byteIndex++] = pixel.a.asDynamic()
        }
    }
}

fun Image.toUint8ClampedArray() = pixels.toUint8ClampedArray(w, h)

class MutableImage(override var w: Int, override var h: Int, pixels: List<Pixel>) : Image {
    constructor(w: Int, h: Int, bytes: Uint8ClampedArray) : this(w, h, bytes.toPixels())

    init {
        check(pixels.size == w * h) { "Given width $w and height $h, so expected ${w * h} pixels, but got ${pixels.size}" }
    }

    override val pixels = pixels.toMutableList()

    override fun copy(): MutableImage = super.copy() as MutableImage
}

operator fun MutableImage.set(x: Int, y: Int, pixel: Pixel) {
    pixels[y * w + x] = pixel
}
