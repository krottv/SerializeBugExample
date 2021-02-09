package com.example.jsonserialize.media

import com.example.jsonserialize.serialization.ColorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("template")
data class Template(
    @Transient
    var path: String = "",
    var premium: Boolean = false,
    var forInstagramSubscribed: Boolean = false,
    val medias: MutableList<Media> = mutableListOf(),
    var palette: Palette = Palette.withBackgroundColor(0),
    @Serializable(with = ColorSerializer::class)
    var backgroundColor: Int = 0,
    var name: String? = null,
    var clipChildren: Boolean = false,
    var preferredDuration: Int = 180,
    var maxDuration: Int? = null,
    var videoDemo: String? = null,
    //This is always not null only in EditActivity. When it is non null?
    //when template is saved. because originally templates don't have this data in assets
    var originalData: OriginalTemplateData? = null,
    //from 0 to duration
    var timeForEdit: Int? = null,
    var format: Int = FORMAT_STORY
) {
    companion object {
        const val TEMPLATE_MAX_POSSIBLE_FRAMES = 5000
        const val TEMPLATE_MIN_POSSIBLE_FRAMES = 30

        const val FORMAT_STORY = 0
        const val FORMAT_HORIZONTAL_FULL_HD = 1
        const val FORMAT_SQUARE = 2
        const val FORMAT_POST_4x5 = 3

        const val NEW_TEXT_MIN_FRAMES = 90
    }
}


@Serializable
class OriginalTemplateData(
    var originalCategory: String,
    var originalIndexInCategory: Int,
    var originalPath: String
)