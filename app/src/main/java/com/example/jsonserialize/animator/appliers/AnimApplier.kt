package com.example.jsonserialize.animator.appliers

import android.graphics.Canvas
import android.view.View
import androidx.annotation.WorkerThread
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AnimApplier {
    /**
     * value - from 0 to 1
     */
    fun onDraw(view: View, canvas: Canvas, value: Float) {

    }

    @WorkerThread
    fun onPrepared(view: View, value: Float) {
    }

    fun onPreDraw(view: View, value: Float) {

    }

    fun initialize(view: View) {

    }

    companion object {
        inline fun calcAnimValue(from: Float, to: Float, value: Float) =
            ((to - from) * value) + from

    }
}

@Serializable
@SerialName("nothing")
class NothingAnimApplier : AnimApplier