package com.example.jsonserialize.animator.appliers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("fade")
class FadeAnimApplier(var from: Float = 0f, var to: Float = 1f) : AnimApplier