package com.example.jsonserialize.serialization

import android.view.Gravity
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object GravitySerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("GravitySerialization", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Int) {
        val string = when (value) {
            Gravity.LEFT, Gravity.NO_GRAVITY -> "left"
            Gravity.RIGHT -> "right"
            Gravity.CENTER_HORIZONTAL -> "center"
            Gravity.START -> "start"
            Gravity.END -> "end"
            else -> throw java.lang.IllegalStateException("unsupported gravity ${value}")
        }
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Int {
        val gravityString = decoder.decodeString()

        return when (gravityString) {
            "left" -> Gravity.LEFT
            "right" -> Gravity.RIGHT
            "center" -> Gravity.CENTER_HORIZONTAL
            "start" -> Gravity.START
            "end" -> Gravity.END
            else -> throw java.lang.IllegalStateException("unsupported gravity $gravityString")
        }
    }
}