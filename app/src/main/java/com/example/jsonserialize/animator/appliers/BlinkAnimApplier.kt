package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
@SerialName("blink")
class BlinkAnimApplier(val blinks: Int) : AnimApplier {

    private fun getAlpha(value: Float): Float {
        val intervals = blinks * 2
        val interval = ceil(intervals * value)

        return if (interval % 2 == 0f) {
            1f
        } else {
            0f
        }
    }

    override fun onPreDraw(view: View, value: Float) {
        view.alpha = getAlpha(value)
    }

}