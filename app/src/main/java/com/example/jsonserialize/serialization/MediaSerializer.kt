package com.example.jsonserialize.serialization

import com.example.jsonserialize.media.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.*

object MediaTextSerializer : MediaSerializer<MediaText>(MediaText.serializer())
object MediaImageSerializer : MediaSerializer<MediaImage>(MediaImage.serializer())
object MediaPathSerializer : MediaSerializer<MediaPath>(MediaPath.serializer())
object MediaLottieSerializer : MediaSerializer<MediaLottie>(MediaLottie.serializer())
object MediaGroupSerializer : MediaSerializer<MediaGroup>(MediaGroup.serializer())


sealed class MediaSerializer<T : Media>(serializer: KSerializer<T>) : JsonTransformingSerializer<T>(serializer) {

    @ExperimentalSerializationApi
    override fun transformDeserialize(element: JsonElement): JsonElement {
        if (element is JsonObject) {

            val map = element.toMutableMap()
            val layoutPositionMap = mutableMapOf<String, JsonElement>()
            val descriptor = LayoutPositionSerializer.descriptor

            for (i in 0 until descriptor.elementsCount) {
                val name = descriptor.getElementName(i)
                val el = map.remove(name)
                if (el != null) {
                    layoutPositionMap[name] = el
                }
            }

            map["layoutPosition"] = JsonObject(layoutPositionMap)
            //map["type"] = JsonPrimitive(descriptor.serialName)

            return JsonObject(map)
        }
        return element
    }

    @ExperimentalSerializationApi
    override fun transformSerialize(element: JsonElement): JsonElement {

        if (element is JsonObject) {

            val map = element.toMutableMap()
            val layoutPosition = map.remove(LayoutPositionSerializer.descriptor.serialName)
            if (layoutPosition != null && layoutPosition is JsonObject) {
                layoutPosition.forEach {
                    map[it.key] = it.value
                }
            }

            //map["type"] = JsonPrimitive(descriptor.serialName)
            return JsonObject(map)
        }

        return element
    }
}