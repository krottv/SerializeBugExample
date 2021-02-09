package com.example.jsonserialize.animator.appliers

import android.graphics.*
import android.view.View
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.max


/**
 * One view can have 2 clipAnimApplier at the same time: Vertical and Horizontal, but only if both have clipDefault = false
 *
 * @param clipDefault - if true then we clip view to its bounds.
 * @param fadePadding - if we need to apply LinearGradient Shader. Percent of parent width
 */
@Serializable
@SerialName("clip")
class ClipAnimApplier(
    @Required
    var direction: String,
    val fadePadding: Float? = null,
    val from: Float = 0f,
    val to: Float = 1f,
    var clipDefault: Boolean = false
) : AnimApplier {

    companion object {
        const val DIRECTION_TOP_TO_BOTTOM = "top_to_bottom"
        const val DIRECTION_LEFT_TO_RIGHT = "left_to_right"
        const val DIRECTION_BOTTOM_TO_TOP = "bottom_to_top"
        const val DIRECTION_CENTER_TO_TOP_AND_BOTTOM = "center_to_top_and_bottom"
        const val DIRECTION_CENTER_TO_LEFT_AND_RIGHT = "center_to_left_and_right"
        const val DIRECTION_RIGHT_TO_LEFT = "right_to_left"
        const val DIRECTION_CIRCULAR = "circular"
        const val DIRECTION_CIRCULAR_INVERSE = "circular_inverse"
        const val DIRECTION_NONE = "none"
    }
}
