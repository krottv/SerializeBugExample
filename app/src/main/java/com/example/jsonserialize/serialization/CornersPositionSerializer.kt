package com.example.jsonserialize.serialization

import com.example.jsonserialize.media.CORNER_RADIUS_ONLY_BOTTOM
import com.example.jsonserialize.media.CORNER_RADIUS_ONLY_TOP
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CornersPositionSerializer : KSerializer<Int> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CornersPositionSerialization", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Int) {
        val string = when (value) {
            CORNER_RADIUS_ONLY_BOTTOM -> "only_bottom"
            CORNER_RADIUS_ONLY_TOP -> "only_top"
            else -> null
        }
        if (string != null)
            encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Int {
        return when (decoder.decodeString()) {
            "only_top" -> CORNER_RADIUS_ONLY_TOP
            "only_bottom" -> CORNER_RADIUS_ONLY_BOTTOM
            else -> 0
        }
    }
}