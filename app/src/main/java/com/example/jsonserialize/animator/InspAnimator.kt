package com.example.jsonserialize.animator

import app.inspiry.animator.InspInterpolator
import com.example.jsonserialize.serialization.AnimatorDurationSerializer
import com.example.jsonserialize.serialization.AnimatorSerializer
import com.example.jsonserialize.serialization.InterpolatorSerializer
import com.example.jsonserialize.animator.appliers.AnimApplier
import kotlinx.serialization.Serializable

//startTime - for outAnimator it increases the whole animation time

@Serializable(with = AnimatorSerializer::class)
class InspAnimator(
    var startFrame: Int = 0,
    @Serializable(with = AnimatorDurationSerializer::class)
    var duration: Int = 0,
    @Serializable(with = InterpolatorSerializer::class)
    var interpolator: InspInterpolator? = null,
    var animationApplier: AnimApplier
) {

    companion object {
        const val DURATION_ALL = -1000000
        const val DURATION_IN = -2000000
        const val DURATION_AS_TEMPLATE = -3000000
    }
}
