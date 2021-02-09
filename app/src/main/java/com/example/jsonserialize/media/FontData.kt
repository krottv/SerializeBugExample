package com.example.jsonserialize.media

import kotlinx.serialization.Serializable

@Serializable
data class FontData(val fontPath: String? = null, var fontStyle: String = FONT_STYLE_REGULAR) {


    companion object {
        const val FONT_STYLE_REGULAR = "regular"
        const val FONT_STYLE_BOLD = "bold"
        const val FONT_STYLE_ITALIC = "italic"
        const val FONT_STYLE_LIGHT = "light"
    }
}