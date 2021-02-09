package com.example.jsonserialize.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PlayerParams

@Serializable
@SerialName("decoder")
data class DecoderPlayerParams(
    var viewStartTimeUs: Long,
    var videoStartTimeUs: Long,
    val totalDurationUs: Long,
    val isLoopEnabled: Boolean
) : PlayerParams() {
    override fun toString(): String {
        return "DecoderPlayerParams(viewStartTimeUs=$viewStartTimeUs, videoStartTimeUs=$videoStartTimeUs, totalDurationUs=$totalDurationUs, isLoopEnabled=$isLoopEnabled)"
    }
}