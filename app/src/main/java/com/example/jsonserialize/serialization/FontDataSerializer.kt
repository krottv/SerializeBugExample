package com.example.jsonserialize.serialization

import com.example.jsonserialize.media.FontData
import kotlinx.serialization.json.*

object FontDataSerializer : JsonTransformingSerializer<FontData>(FontData.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        if (element is JsonPrimitive) {

            return JsonObject(mapOf("fontPath" to element.jsonPrimitive))
        }
        return element
    }

    override fun transformSerialize(element: JsonElement): JsonElement {


        if (element is JsonObject && element["fontPath"] != null) {

            val style = element["fontStyle"]?.jsonPrimitive?.contentOrNull
            if (style == null || style == FontData.FONT_STYLE_REGULAR)
                return element["fontPath"]!!
        }

        return element
    }
}