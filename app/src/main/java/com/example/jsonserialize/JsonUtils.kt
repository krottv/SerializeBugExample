package com.example.jsonserialize

import android.graphics.Color
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.jsonserialize.animator.appliers.*
import com.example.jsonserialize.media.Media
import com.example.jsonserialize.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val json = Json {
    allowStructuredMapKeys = true
    prettyPrint = true
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {

        polymorphic(AnimApplier::class) {
            subclass(BackgroundColorAnimApplier::class)
            subclass(BlinkAnimApplier::class)
            subclass(BlurAnimApplier::class)
            subclass(ClipAnimApplier::class)
            subclass(ElevationAnimApplier::class)
            subclass(FadeAnimApplier::class)
            subclass(LetterSpacingAnimApplier::class)
            subclass(MoveAnimApplier::class)
            subclass(MoveToXAnimApplier::class)
            subclass(MoveToYAnimApplier::class)
            subclass(MoveInnerAnimApplier::class)
            subclass(RadiusAnimApplier::class)
            subclass(RotateAnimApplier::class)
            subclass(ScaleInnerAnimApplier::class)
            subclass(ScaleAnimSerializer)
            subclass(SizeAnimApplier::class)
        }

        polymorphic(Media::class) {
            subclass(MediaTextSerializer)
            subclass(MediaImageSerializer)
            subclass(MediaLottieSerializer)
            subclass(MediaGroupSerializer)
            subclass(MediaPathSerializer)
        }
    }
}


fun String?.parseColor(fallbackColor: Int = Color.TRANSPARENT): Int {
    return if (this.isNullOrEmpty()) fallbackColor
    else try {

        // Use a long to avoid rollovers on #ffXXXXXX
        var color: Long = substring(1).toLong(16)
        if (length == 7) {
            // Set the alpha value
            color = color or -0x1000000
        } else require(length == 9) { "Unknown color" }
        return color.toInt()

    } catch (e: IllegalArgumentException) {
        fallbackColor
    }
}


fun Int.colorToString(): String {
    return "#${alpha.toStringComponent()}${red.toStringComponent()}${green.toStringComponent()}${blue.toStringComponent()}"
}


private fun Int.toStringComponent(): String =
    this.toString(16).let { if (it.length == 1) "0${it}" else it }
