package com.example.jsonserialize.serialization

import com.example.jsonserialize.animator.appliers.ScaleOuterAnimApplier
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

object ScaleAnimSerializer : KSerializer<ScaleOuterAnimApplier> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("scale") {
        element<Float>("from")
        element<Float>("to")
        element<Float>("fromX")
        element<Float>("toX")
        element<Float>("fromY")
        element<Float>("toY")
    }

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: ScaleOuterAnimApplier) =
        ScaleOuterAnimApplier.serializer().serialize(encoder, value)

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): ScaleOuterAnimApplier {

        // Cast to JSON-specific interface
        val jsonDecoder = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
        // Read the whole content as JSON
        val obj = jsonDecoder.decodeJsonElement().jsonObject.toMutableMap()

        val from = obj["from"]?.jsonPrimitive?.floatOrNull
        val to = obj["to"]?.jsonPrimitive?.floatOrNull

        fun getValue(main: Float?, fallback: String, default: Float = 1f) =
            main ?: obj[fallback]?.jsonPrimitive?.floatOrNull ?: default


        return ScaleOuterAnimApplier(getValue(from, "fromX"), getValue(to, "toX"),
            getValue(from, "fromY"), getValue(to, "toY"))
    }
}