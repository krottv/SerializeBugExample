package com.example.jsonserialize.serialization

import android.view.Gravity
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

object AnchorSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("gravity") {
            element<String?>("anchorX")
            element<String?>("anchorY")
        }

    override fun serialize(encoder: Encoder, value: Int) {
        var anchorX: String? = null
        var anchorY: String? = null

        val gravityVertical = value and Gravity.VERTICAL_GRAVITY_MASK
        if (gravityVertical == Gravity.CENTER_VERTICAL) anchorY = "center"
        else if (gravityVertical == Gravity.BOTTOM) anchorY = "bottom"

        val gravityHorizontal = value and Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
        if (gravityHorizontal == Gravity.CENTER_HORIZONTAL) anchorX = "center"
        else if (gravityHorizontal == Gravity.END) anchorX = "end"

        encoder.encodeStructure(descriptor) {
            if (anchorX != null)
                encodeStringElement(descriptor, 0, anchorX)
            if (anchorY != null)
                encodeStringElement(descriptor, 1, anchorY)
        }
    }

    override fun deserialize(decoder: Decoder): Int {

        decoder.decodeStructure(descriptor) {
            var anchorX: String? = null
            var anchorY: String? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> anchorX = decodeStringElement(descriptor, 0)
                    1 -> anchorY = decodeStringElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            val gravityHorizontal = when (anchorX) {
                "", null -> 0
                "start" -> Gravity.START
                "end" -> Gravity.END
                "center" -> Gravity.CENTER_HORIZONTAL
                else -> throw java.lang.IllegalArgumentException("wrong anchorX ${anchorX}")
            }


            val gravityVertical = when (anchorY) {
                "", null -> 0
                "top" -> Gravity.TOP
                "bottom" -> Gravity.BOTTOM
                "center" -> Gravity.CENTER_VERTICAL
                else -> throw java.lang.IllegalArgumentException("wrong anchorY ${anchorY}")
            }

            return gravityHorizontal or gravityVertical
        }
    }
}