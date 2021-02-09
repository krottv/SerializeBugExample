package com.example.jsonserialize.animator.appliers

import com.example.jsonserialize.animator.appliers.AnimApplier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("radius")
class RadiusAnimApplier(var from: Float = 0f, var to: Float = 0f) : AnimApplier