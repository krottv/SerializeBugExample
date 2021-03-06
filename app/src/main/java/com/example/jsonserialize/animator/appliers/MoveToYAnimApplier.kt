package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("move_to_y")
class MoveToYAnimApplier(
    var from: Float = 0f,
    var to: Float = 0f,
    var relativeToParent: Boolean = true,
) : AnimApplier