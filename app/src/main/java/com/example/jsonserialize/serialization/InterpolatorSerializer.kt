package com.example.jsonserialize.serialization
import app.inspiry.animator.InspInterpolator
import kotlinx.serialization.json.*

object InterpolatorSerializer : JsonTransformingSerializer<InspInterpolator>(InspInterpolator.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        if (element is JsonPrimitive) {

            return JsonObject(interpolatorByType(element.jsonPrimitive.content).also {
                it["type"] = JsonPrimitive("path")
            })
        }
        return element
    }

    fun interpolatorByType(type: String): MutableMap<String, JsonElement> {

        val map = HashMap<String, JsonElement>()

        fun putVals(
            controlX1: Float,
            controlY1: Float,
            controlX2: Float,
            controlY2: Float
        ) {
            map["x1"] = JsonPrimitive(controlX1)
            map["x2"] = JsonPrimitive(controlX2)
            map["y1"] = JsonPrimitive(controlY1)
            map["y2"] = JsonPrimitive(controlY2)
        }

        //moderate
        if (type == "cubicInOut") {
            putVals(0.645f, 0.045f, 0.355f, 1.0f)
        } else if (type == "cubicIn") {
            putVals(0.55f, 0.055f, 0.675f, 0.19f)
        } else if (type == "cubicOut") {
            putVals(0.215f, 0.61f, 0.355f, 1.0f)
        }

        //intense
        else if (type == "expIn") {
            putVals(0.95f, 0.05f, 0.795f, 0.035f)
        } else if (type == "expOut") {
            putVals(0.19f, 1.0f, 0.22f, 1.0f)
        }

        //they have two phases: begins with linear and then does expOut. First is more smooth, has less linear phase
        else if (type == "linear25expOut") {
            putVals(0.25f, 0.25f, 0.0f, 1.0f)
        } else if (type == "linear50expOut") {
            putVals(0.5f, 0.5f, 0.0f, 1.0f)
        }

        //more smooth than linear
        else if (type == "flatIn25expOut") {
            putVals(0.25f, 0.0f, 0.0f, 1.0f)
        } else if (type == "flatIn50expOut") {
            putVals(0.5f, 0.0f, 0.0f, 1.0f)
        } else if (type == "flatInExpOut") {
            putVals(0.75f, 0.0f, 0.0f, 1.0f)
        }

        //slow - fast - slow
        else if (type == "easeInOutQuint") {
            putVals(0.86f, 0.0f, 0.07f, 1.0f)
        } else if (type == "fastInSuperfastOut1") {
            putVals(0.0f, 0.5f, 1.0f, 0.0f)
        }

        //slow - fast - slow, but smoother than easeInOutQuint
        else if (type == "fastInSuperfastOut1Invert") {
            putVals(0.5f, 0.0f, 0.0f, 1.0f)

            //twice lighter than the previous
        } else if (type == "easeInOutQuintLight") {

            putVals(0.65f, 0.25f, 0.2f, 1.0f)
        } else if (type == "slowInExpOut") {
            putVals(0.75f, 0.25f, 0.0f, 1.0f)
        }

        //used in yoga floating template
        else if (type == "easeInOut") {
            putVals(0.1f, 0.3f, 0.7f, 0.9f)
        }

        //ease - light, smooth interpolators
        else if (type == "easeIn") {
            putVals(0.42f, 0f, 0.58f, 1.0f)
        } else if (type == "easeOut") {
            putVals(0f, 0f, 0.58f, 1.0f)

        } else if (type == "easeInLight") {
            putVals(0.25f, 0f, 0.65f, 0.64f)

        } else if (type == "easeOutLight") {
            putVals(0.25f, 0.25f, 0.55f, 0.71f)

        } else if (type == "easeInOutLight") {
            putVals(0.28f, 0f, 0.68f, 1f)
        } else if (type == "halloweenInOut") {
            putVals(0f, 0f, 0f, 1f)
        } else if (type == "beautyOut") {
            putVals(0.18f, 0.1f, 0.5f, 1f)

        } else if (type == "elegantlySlowOut") {
            putVals(0.25f, 0.1f, 0.25f, 1f)

        } else {
            throw IllegalArgumentException("Unknown interpolator $type")
        }

        return map
    }
}