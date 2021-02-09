package com.example.jsonserialize.animator.appliers

import com.example.jsonserialize.animator.appliers.AnimApplier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("move_inner")
class MoveInnerAnimApplier(var fromX: Float = 0f, var fromY: Float = 0f, var toX: Float = 0f, var toY: Float = 0f) :
    AnimApplier