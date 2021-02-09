package com.example.jsonserialize.animator

import kotlinx.serialization.Serializable

//used to apply animation to a specific line, word
@Serializable
data class TextAnimatorGroups(val group: String, val animators: List<InspAnimator>) {

    fun shouldApplyAnimation(partIndex: Int, partsCount: Int, group: String = this.group): Boolean {
        if (group == ANIMATOR_GROUP_ALL) return true

        if (group == ANIMATOR_GROUP_FIRST && partIndex == 0) return true

        if (group == ANIMATOR_GROUP_EVEN && partIndex % 2 == 1) return true

        if (group == ANIMATOR_GROUP_EVEN_OR_FIRST && (partsCount == 1 || partIndex % 2 == 1)) return true

        if (group == ANIMATOR_GROUP_UNEVEN && partIndex % 2 == 0) return true

        if (group == ANIMATOR_GROUP_UNEVEN_NOT_FIRST && (partsCount != 1 && partIndex % 2 == 0)) return true

        return false
    }

    fun showApplyShadowAnimation(partIndex: Int, partsCount: Int): Boolean {
        val s = group.split('_')
        if (s.size > 1) {
            return shouldApplyAnimation(partIndex, partsCount, s[1])
        }
        return false
    }
}

const val ANIMATOR_GROUP_ALL = "all"
const val ANIMATOR_GROUP_EVEN = "even"
const val ANIMATOR_GROUP_EVEN_OR_FIRST = "even_or_first"
const val ANIMATOR_GROUP_UNEVEN_NOT_FIRST = "uneven_not_first"
const val ANIMATOR_GROUP_UNEVEN = "uneven"
const val ANIMATOR_GROUP_FIRST = "first"
