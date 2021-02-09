package com.example.jsonserialize.animator.appliers

import android.graphics.Color
import com.example.jsonserialize.serialization.ColorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("backgroundColor")
class BackgroundColorAnimApplier(
    @Serializable(with = ColorSerializer::class) val from: Int,
    @Serializable(with = ColorSerializer::class) val to: Int,
    @Serializable(with = ColorSerializer::class) val fromEnd: Int? = null,
    @Serializable(with = ColorSerializer::class) val toEnd: Int? = null
) : AnimApplier {

    fun colorDistance(color1: Int, color2: Int, delta: Float): Int {
        return colorDistance(
            Color.alpha(color1),
            Color.red(color1),
            Color.green(color1),
            Color.blue(color1),
            Color.alpha(color2),
            Color.red(color2),
            Color.green(color2),
            Color.blue(color2),
            delta)
    }

    fun colorDistance(a1: Int,
                      r1: Int,
                      g1: Int,
                      b1: Int,
                      a2: Int,
                      r2: Int,
                      g2: Int,
                      b2: Int,
                      delta: Float): Int {
        val reverse = 1f - delta

        val a = Math.round(a1 * delta + a2 * reverse)
        val r = Math.round(r1 * delta + r2 * reverse)
        val g = Math.round(g1 * delta + g2 * reverse)
        val b = Math.round(b1 * delta + b2 * reverse)

        return Color.argb(a, r, g, b)
    }

}