package com.example.jsonserialize.media

import kotlinx.serialization.Serializable


@Serializable
data class LayoutPosition(
    var width: String, var height: String,
    var gravity: Int = 0,
    var x: String? = null,
    var y: String? = null,
    var paddingEnd: String? = null,
    var paddingBottom: String? = null,
    var paddingStart: String? = null,
    var paddingTop: String? = null
)