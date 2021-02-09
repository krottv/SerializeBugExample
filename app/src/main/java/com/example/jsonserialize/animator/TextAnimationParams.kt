package com.example.jsonserialize.animator

import app.inspiry.animator.InspInterpolator
import com.example.jsonserialize.serialization.InterpolatorSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TextAnimationParams(
    var textAnimatorGroups: List<TextAnimatorGroups>? = null,
    var backgroundAnimatorGroups: List<TextAnimatorGroups>? = null,

    var charDelayMillis: Double = 0.0,
    var wordDelayMillis: Double = 0.0,
    var lineDelayMillis: Double = 0.0,

    @Serializable(with = InterpolatorSerializer::class)
    val charInterpolator: InspInterpolator? = null,
    @Serializable(with = InterpolatorSerializer::class)
    val wordInterpolator: InspInterpolator? = null,
    @Serializable(with = InterpolatorSerializer::class)
    val lineInterpolator: InspInterpolator? = null,

    val shuffle: Boolean = false,
    val charDelayBetweenWords: Boolean = false,
    val reverse: Boolean = false
)