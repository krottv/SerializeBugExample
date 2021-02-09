package com.example.jsonserialize.serialization

import com.example.jsonserialize.json
import com.example.jsonserialize.media.LayoutPosition
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

object LayoutPositionSerializer : KSerializer<LayoutPosition> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("layoutPosition") {
        element<String>("width")
        element<String>("height")
        element<String?>("x")
        element<String?>("y")
        element<String?>("paddingStart")
        element<String?>("paddingEnd")
        element<String?>("paddingBottom")
        element<String?>("paddingTop")
        element<String?>("anchorX")
        element<String?>("anchorY")
        element<String?>("padding")
    }

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: LayoutPosition) {

        val jsonEncoder = encoder as? JsonEncoder ?: error("Can be serialized only by JSON")

        val gravity = value.gravity

        val map = json.encodeToJsonElement(value).jsonObject.toMutableMap()
        map.remove(AnchorSerializer.descriptor.serialName)

        val mapGravity = json.encodeToJsonElement(AnchorSerializer, gravity).jsonObject.toMap()
        map.putAll(mapGravity)

        jsonEncoder.encodeJsonElement(JsonObject(map))
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): LayoutPosition {

        // Cast to JSON-specific interface
        val jsonDecoder = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
        // Read the whole content as JSON
        val obj = jsonDecoder.decodeJsonElement().jsonObject.toMutableMap()

        val mergedSerializer = AnchorSerializer
        val objMerged = mutableMapOf<String, JsonElement>()

        for (i in 0 until mergedSerializer.descriptor.elementsCount) {
            val name = mergedSerializer.descriptor.getElementName(i)

            val element = obj[name]
            if (element != null)
                objMerged[name] = element
            obj.remove(name)
        }

        val padding = obj["padding"]?.jsonPrimitive
        if (padding?.contentOrNull != null) {
            obj["paddingStart"] = padding
            obj["paddingEnd"] = padding
            obj["paddingTop"] = padding
            obj["paddingBottom"] = padding
        }

        val layoutPosition: LayoutPosition = json.decodeFromJsonElement(JsonObject(obj))
        val gravity = json.decodeFromJsonElement(AnchorSerializer, JsonObject(objMerged))
        layoutPosition.gravity = gravity

        return layoutPosition
    }
}