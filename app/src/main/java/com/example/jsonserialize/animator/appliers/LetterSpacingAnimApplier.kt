package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("letter_spacing")
class LetterSpacingAnimApplier(val from: Float, val to: Float) : AnimApplier