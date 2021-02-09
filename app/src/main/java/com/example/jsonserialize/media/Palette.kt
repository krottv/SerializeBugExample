package com.example.jsonserialize.media

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.example.jsonserialize.serialization.ColorSerializer
import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param backgroundVideoStartMs if it is non null, then backgroundImage is actually a video
 */
@Serializable
class Palette(
    val isAvailable: Boolean = true,
    @Serializable(with = ColorSerializer::class) val defaultTextColor: Int = Color.BLACK,
    val bgImageOrGradientCanBeSet: Boolean = true,
    var backgroundImage: String? = null, var backgroundGradient: PaletteLinearGradient? = null,
    var backgroundVideoStartMs: Int? = null,
    var backgroundVideoLooped: Boolean? = null,
    var choices: ArrayList<PaletteChoice> = arrayListOf()
) {

    companion object {

        fun withBackgroundColor(color: Int): Palette {
            return Palette(choices = arrayListOf(PaletteChoice(color, listOf(PaletteChoiceElement("background", null)))))
        }

        fun emptyPalette() = Palette(choices = arrayListOf())

    }

    override fun toString(): String {
        return "Palette(isAvailable=$isAvailable, defaultTextColor=$defaultTextColor, bgImageOrGradientCanBeSet=$bgImageOrGradientCanBeSet, backgroundImage=$backgroundImage, backgroundGradient=$backgroundGradient)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Palette

        if (backgroundImage != other.backgroundImage) return false
        if (backgroundGradient != other.backgroundGradient) return false
        if (choices != other.choices) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundImage?.hashCode() ?: 0
        result = 31 * result + (backgroundGradient?.hashCode() ?: 0)
        result = 31 * result + choices.hashCode()
        return result
    }
}

@Serializable
sealed class AbsPaletteColor()


@Serializable()
@SerialName("linear")
class PaletteLinearGradient(
    var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT,
    val colors: List<@Serializable(with = ColorSerializer::class) Int> = arrayListOf(), val offsets: FloatArray? = null
) : AbsPaletteColor()

@Serializable
class PaletteChoice(
    @Serializable(with = ColorSerializer::class) var color: Int? = null,
    @Required val elements: List<PaletteChoiceElement>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaletteChoice

        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        return color ?: 0
    }
}

@Serializable
class PaletteChoiceElement(@Required val type: String, val id: String? = null)
