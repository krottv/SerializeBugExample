package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("move")
class MoveAnimApplier(
    var fromX: Float = 0f,
    var fromY: Float = 0f,
    var toX: Float = 0f,
    var toY: Float = 0f,
    var relativeToParent: Boolean = true,
) : AnimApplier