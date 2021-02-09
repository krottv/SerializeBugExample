package com.example.jsonserialize.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlin.math.roundToInt

object CharDelaySerializer : MillisSerializer("charDelay", "charDelay", "charDelayMillis")
object WordDelaySerializer : MillisSerializer("wordDelay", "wordDelay", "wordDelayMillis")
object LineDelaySerializer : MillisSerializer("lineDelay", "lineDelay", "lineDelayMillis")
object StartTimeSerializer : FrameSerializer("start", "startFrame", "startTimeMillis")
object DelayBeforeEndTimeSerializer : FrameSerializer("delay", "delayBeforeEnd", "delayBeforeEndMillis")
object LoopedAnimationIntervalTimeSerializer : FrameSerializer("looped", "loopedAnimationInterval", "loopedAnimationIntervalMillis")


open class MillisSerializer(val serialName: String, val frameName: String, val millisName: String) : KSerializer<Double> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(serialName) {
            element<Int?>(frameName)
            element<Double?>(millisName)
        }

    override fun serialize(encoder: Encoder, value: Double) {
        encoder.encodeStructure(descriptor) {
            encodeDoubleElement(descriptor, 1, value)
        }
    }

    override fun deserialize(decoder: Decoder): Double {

        decoder.decodeStructure(descriptor) {
            var startFrame: Int? = null
            var startTimeMillis: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> startFrame = decodeIntElement(descriptor, 0)
                    1 -> startTimeMillis = decodeDoubleElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            return if (startTimeMillis != null) startTimeMillis
            else if (startFrame != null) startFrame * 33.33333
            else 0.0
        }
    }
}

open class FrameSerializer(val serialName: String, val frameName: String, val millisName: String) : KSerializer<Int> {

    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor(serialName) {
            element<Int?>(frameName)
            element<Double?>(millisName)
        }

    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value)
        }
    }

    override fun deserialize(decoder: Decoder): Int {

        decoder.decodeStructure(descriptor) {
            var startFrame: Int? = null
            var startTimeMillis: Double? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> startFrame = decodeIntElement(descriptor, 0)
                    1 -> startTimeMillis = decodeDoubleElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            return if (startFrame != null) startFrame
            else if (startTimeMillis != null) (startTimeMillis / 33.3333).roundToInt()
            else 0
        }
    }
}
