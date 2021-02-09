package com.example.jsonserialize.serialization

import com.example.jsonserialize.animator.ANIMATOR_GROUP_ALL
import com.example.jsonserialize.animator.TextAnimationParams
import com.example.jsonserialize.animator.TextAnimatorGroups
import com.example.jsonserialize.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object TextAnimationParamsSerializer : KSerializer<TextAnimationParams> {
    override val descriptor: SerialDescriptor = TextAnimationParams.serializer().descriptor

    @ExperimentalSerializationApi
    override fun serialize(encoder: Encoder, value: TextAnimationParams) {

        val jsonEncoder = encoder as? JsonEncoder ?: error("Can be serialized only by JSON")

        val map = json.encodeToJsonElement(value).jsonObject.toMutableMap()

        fun unpackAnimatorGroups(it: List<TextAnimatorGroups>, keyGroups: String, keyAnimators: String) {
            if (it.size == 1 && it[0].group == ANIMATOR_GROUP_ALL) {

                val backgroundAnimatorJson = map.remove(keyGroups)

                if (backgroundAnimatorJson != null) {
                    val animators = backgroundAnimatorJson.jsonArray[0].jsonObject["animators"]
                    if (animators != null)
                        map[keyAnimators] = animators
                }
            }
        }
        value.backgroundAnimatorGroups?.let {
            unpackAnimatorGroups(it, "backgroundAnimatorGroups", "backgroundAnimators")
        }
        value.textAnimatorGroups?.let {
            unpackAnimatorGroups(it, "textAnimatorGroups", "textAnimators")
        }


        jsonEncoder.encodeJsonElement(JsonObject(map))
    }

    @ExperimentalSerializationApi
    override fun deserialize(decoder: Decoder): TextAnimationParams {

        // Cast to JSON-specific interface
        val jsonDecoder = decoder as? JsonDecoder ?: error("Can be deserialized only by JSON")
        // Read the whole content as JSON
        val obj = jsonDecoder.decodeJsonElement().jsonObject.toMutableMap()

        fun unpackAnimators(animatorsKey: String, animatorGroupKey: String) {
            val animators = obj.remove(animatorsKey)
            if (animators != null) {
                val textGroupAnimators =
                    JsonArray(listOf(JsonObject(mapOf("group" to JsonPrimitive(ANIMATOR_GROUP_ALL), "animators" to animators))))
                obj[animatorGroupKey] = textGroupAnimators
            }
        }

        unpackAnimators("textAnimators", "textAnimatorGroups")
        unpackAnimators("backgroundAnimators", "backgroundAnimatorGroups")

        fun <T> extractObject(mergedSerializer: KSerializer<T>): T {
            val objMerged = mutableMapOf<String, JsonElement>()
            for (i in 0 until mergedSerializer.descriptor.elementsCount) {
                val name = mergedSerializer.descriptor.getElementName(i)

                val element = obj[name]
                if (element != null)
                    objMerged[name] = element
                obj.remove(name)
            }

            return json.decodeFromJsonElement(mergedSerializer, JsonObject(objMerged))
        }


        val charDelay = extractObject(CharDelaySerializer)
        val wordDelay = extractObject(WordDelaySerializer)
        val lineDelay = extractObject(LineDelaySerializer)

        val result: TextAnimationParams = json.decodeFromJsonElement(JsonObject(obj))
        result.charDelayMillis = charDelay
        result.wordDelayMillis = wordDelay
        result.lineDelayMillis = lineDelay

        return result
    }
}