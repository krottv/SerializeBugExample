package com.example.jsonserialize.serialization

import com.example.jsonserialize.colorToString
import com.example.jsonserialize.parseColor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ColorSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ColorSerialization", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Int) {
        val string = value.colorToString()
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Int {
        val string = decoder.decodeString()
        return string.parseColor()
    }
}