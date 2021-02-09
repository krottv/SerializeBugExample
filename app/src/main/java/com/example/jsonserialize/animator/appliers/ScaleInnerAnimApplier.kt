package com.example.jsonserialize.animator.appliers

import android.view.View
import com.example.jsonserialize.animator.appliers.AnimApplier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("scale_inner")
class ScaleInnerAnimApplier(var from: Float = 1f, var to: Float = 1f) : AnimApplier