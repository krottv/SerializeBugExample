package com.example.jsonserialize.media

import kotlinx.serialization.Serializable

@Serializable
class ProgramCreator(
    val vertexShader: String,
    val fragmentShader: String,
    val textures: List<TextureCreator>
) {
    fun getEditableTexturesCount() = textures.filter { it.type.isEdit() }.size
}