package com.example.jsonserialize.serialization

import com.example.jsonserialize.animator.InspAnimator
import app.inspiry.animator.InspInterpolator
import com.example.jsonserialize.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlin.math.roundToInt

object AnimatorSerializer : KSerializer<InspAnimator> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Animator") {
        element<Int?>("startFrame")
        element<Double?>("startTimeMillis")
        element<Int?>("duration")
        element<Double?>("durationMillis")
        element<JsonElement?>("interpolator")
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): InspAnimator {
        // Cast to JSON-specific interface
        val jsonDecoder = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
        // Read the whole content as JSON
        val obj = jsonDecoder.decodeJsonElement().jsonObject

        val startFrame = json.decodeFromJsonElement(StartTimeSerializer, obj)

        var duration = 0
        if (obj.containsKey("duration")) {

            duration = obj["duration"]?.let {
                json.decodeFromJsonElement(AnimatorDurationSerializer, it)
            } ?: 0

        } else if (obj.containsKey("durationMillis")) {

            duration = obj["durationMillis"]?.let {

                if (it.jsonPrimitive.isString)
                    json.decodeFromJsonElement(AnimatorDurationSerializer, it)
                else
                    (it.jsonPrimitive.double / 33.333333).roundToInt()

            } ?: 0
        }

        val interpolator: InspInterpolator? = obj["interpolator"]?.let {
            json.decodeFromJsonElement(InterpolatorSerializer, it)
        }

        val animApplier = obj.toMutableMap()
        descriptor.elementNames.forEach {
            animApplier.remove(it)
        }

        return InspAnimator(startFrame, duration, interpolator, json.decodeFromJsonElement(JsonObject(animApplier)))
    }

    override fun serialize(encoder: Encoder, value: InspAnimator) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("Can be serialized only by JSON")

        val map = mutableMapOf<String, JsonElement>()
        if (value.startFrame != 0)
            map["startFrame"] = JsonPrimitive(value.startFrame)
        if (value.duration != 0)
            map["duration"] = JsonPrimitive(value.duration)

        value.interpolator?.let {
            map["interpolator"] = json.encodeToJsonElement(InterpolatorSerializer, it)
        }

        val applier = json.encodeToJsonElement(value.animationApplier).jsonObject.toMap()

        applier.forEach { entry ->
            map[entry.key] = entry.value
        }

        jsonEncoder.encodeJsonElement(JsonObject(map))
    }
}