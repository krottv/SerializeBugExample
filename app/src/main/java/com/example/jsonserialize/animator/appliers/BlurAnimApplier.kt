package com.example.jsonserialize.animator.appliers

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("blur")
class BlurAnimApplier(
    var from: Float = 0f,
    @Required
    var to: Float
) : AnimApplier