package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("scale")
class ScaleOuterAnimApplier(var fromX: Float = 1f, var toX: Float = 1f, var fromY: Float = 1f, var toY: Float = 1f) :
    AnimApplier