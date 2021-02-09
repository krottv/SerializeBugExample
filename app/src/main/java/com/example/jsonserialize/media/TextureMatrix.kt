package com.example.jsonserialize.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The class is used to change texture overlay
 */
@Serializable
sealed class TextureMatrix {
    abstract val name: Int
}


/**
 * Save aspect-ratio image setting
 */
@Serializable
@SerialName("aspect-ratio")
class AspectRatioTextureMatrix(override val name: Int) : TextureMatrix()

/**
 * Clip texture from image
 */
@Serializable
@SerialName("clip")
class ClipTextureMatrix(
    override val name: Int,
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) : TextureMatrix()

/**
 * Transform texture from image
 */
@Serializable
@SerialName("transform")
class TransformTextureMatrix(override val name: Int) : TextureMatrix()