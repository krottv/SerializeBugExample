package com.example.jsonserialize.animator.appliers

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("size")
class SizeAnimApplier(val fromWidth: Float = -1f, val fromHeight: Float = -1f,
                      val toWidth: Float = -1f, val toHeight: Float = -1f) :
    AnimApplier