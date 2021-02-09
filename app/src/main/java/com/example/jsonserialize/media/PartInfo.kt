package com.example.jsonserialize.media

data class PartInfo(
    val index: Int, val length: Int, var startTime: Double, val type: TextPartType,
    //if the type is line, then subParts would have type word and so on..
    val subParts: ArrayList<PartInfo>?
)