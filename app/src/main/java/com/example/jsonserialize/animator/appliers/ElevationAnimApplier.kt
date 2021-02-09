package com.example.jsonserialize.animator.appliers

import com.example.jsonserialize.animator.appliers.AnimApplier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("elevation")
class ElevationAnimApplier(var from: Float = 0f, var to: Float) : AnimApplier