package app.inspiry.animator

import android.view.animation.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.cos
import kotlin.math.pow

@Serializable
sealed class InspInterpolator {
    abstract fun getInterpolation(input: Float): Float
}

//https://evgenii.com/blog/spring-button-animation-on-android/
@Serializable
@SerialName("spring")
class InspSpringInterpolator(var amplitude: Double, var frequency: Double) : InspInterpolator() {

    override fun getInterpolation(input: Float): Float {
        return (-1 * Math.E.pow(-input / amplitude) * cos(frequency * input) + 1).toFloat()
    }
}

@Serializable
@SerialName("path")
class InspPathInterpolator(
    var x1: Float,
    var y1: Float,
    var x2: Float,
    var y2: Float
) : InspInterpolator() {

    @Transient
    val inner = PathInterpolator(x1, y1, x2, y2)
    override fun getInterpolation(input: Float) = inner.getInterpolation(input)
}

@Serializable
@SerialName("overshoot")
class InspOvershootInterpolator(var tension: Float) : InspInterpolator() {

    @Transient
    private val inner = OvershootInterpolator(tension)
    override fun getInterpolation(input: Float): Float = inner.getInterpolation(input)
}

@Serializable
@SerialName("decelerate")
class InspDecelerateInterpolator(var factor: Float) : InspInterpolator() {

    @Transient
    private val inner = DecelerateInterpolator(factor)
    override fun getInterpolation(input: Float): Float = inner.getInterpolation(input)
}

@Serializable
@SerialName("anticipateOvershoot")
class InspAnticipateOvershootInterpolator(var tension: Float) : InspInterpolator() {

    @Transient
    private val inner = AnticipateOvershootInterpolator(tension)
    override fun getInterpolation(input: Float): Float = inner.getInterpolation(input)
}


@Serializable
@SerialName("anticipate")
class InspAnticipateInterpolator(var tension: Float) : InspInterpolator() {

    @Transient
    private val inner = AnticipateInterpolator(tension)
    override fun getInterpolation(input: Float): Float = inner.getInterpolation(input)
}

@Serializable
@SerialName("accelerate")
class InspAccelerateInterpolator(var factor: Float) : InspInterpolator() {

    @Transient
    private val inner = AccelerateInterpolator(factor)
    override fun getInterpolation(input: Float): Float = inner.getInterpolation(input)
}