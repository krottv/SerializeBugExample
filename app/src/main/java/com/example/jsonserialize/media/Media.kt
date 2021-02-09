@file:UseSerializers(
    MediaPathSerializer::class, MediaTextSerializer::class,
    MediaImageSerializer::class, MediaLottieSerializer::class, MediaGroupSerializer::class)

package com.example.jsonserialize.media

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.view.Gravity
import com.example.jsonserialize.animator.InspAnimator
import app.inspiry.animator.InspInterpolator
import com.example.jsonserialize.animator.TextAnimationParams
import com.example.jsonserialize.serialization.*
import kotlinx.serialization.*
import java.util.*

@Serializable
abstract class Media {
    @Serializable(with = LayoutPositionSerializer::class)
    abstract var layoutPosition: LayoutPosition
    abstract var id: String?
    abstract var translationX: Float
    abstract var translationY: Float
    abstract var rotation: Float

    @Serializable(with = ColorSerializer::class)
    abstract var backgroundColor: Int
    abstract var textureIndex: Int?

    //min duration can be decreased for InspTextView in timeline. For others not.
    @Serializable(with = MinDurationSerializer::class)
    abstract var minDuration: Int

    //We dont use this startTime in calculating duration of outAnimations
    abstract var startFrame: Int

    //is added to total duration.
    //if there's out animator, then this is added between out and other durations. Otherwise it adds delay before end.

    abstract var delayBeforeEnd: Int
    abstract var animatorsIn: MutableList<InspAnimator>
    abstract var animatorsOut: MutableList<InspAnimator>

    /**
     * These animators can have dynamic durations + it doesn't count in total duration of a media.
     */
    abstract var animatorsAll: MutableList<InspAnimator>

    //if non null - loop animation
    abstract var loopedAnimationInterval: Int?

    abstract var canMoveY: Boolean?
    abstract var canMoveX: Boolean?

    //if true then it can be selected and moved like textView
    abstract var isMovable: Boolean?

    companion object {

        const val MIN_DURATION_AS_TEMPLATE = -1 * 1000000
        const val DEFAULT_TEXT_DELAY_BEFORE_END = 60
    }
}


@Serializable
@SerialName("group")
class MediaGroup(
    val clipChildren: Boolean? = null, val medias: ArrayList<Media> = arrayListOf(),

    @Serializable(with = LayoutPositionSerializer::class)
    override var layoutPosition: LayoutPosition, override var id: String? = null, override var translationX: Float = 0f,
    override var translationY: Float = 0f, override var rotation: Float = 0f,
    @Serializable(with = ColorSerializer::class)
    override var backgroundColor: Int = 0, override var textureIndex: Int? = null,

    @Serializable(with = MinDurationSerializer::class)
    override var minDuration: Int = 0,
    override var startFrame: Int = 0,
    override var delayBeforeEnd: Int = 0,
    override var animatorsIn: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsOut: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsAll: MutableList<InspAnimator> = Collections.emptyList(),
    override var loopedAnimationInterval: Int? = null,
    override var canMoveY: Boolean? = null,
    override var canMoveX: Boolean? = null,
    override var isMovable: Boolean? = null
) : Media()


@Serializable
@SerialName("image")
data class MediaImage(
    @Serializable(with = LayoutPositionSerializer::class)
    override var layoutPosition: LayoutPosition, override var id: String? = null, override var translationX: Float = 0f,
    override var translationY: Float = 0f, override var rotation: Float = 0f,
    @Serializable(with = ColorSerializer::class)
    override var backgroundColor: Int = 0, override var textureIndex: Int? = null,
    @Serializable(with = MinDurationSerializer::class)
    override var minDuration: Int = 0,
    override var startFrame: Int = 0,
    override var delayBeforeEnd: Int = 0,
    override var animatorsIn: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsOut: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsAll: MutableList<InspAnimator> = Collections.emptyList(),
    override var loopedAnimationInterval: Int? = null,
    override var canMoveY: Boolean? = null,
    override var canMoveX: Boolean? = null, override var isMovable: Boolean? = null

) : Media() {

    var demoSource: String? = null
    var borderType: String? = null

    @Serializable(with = ColorSerializer::class)
    var borderColor: Int? = null
    var borderWidth: String? = null

    var isEditable: Boolean = true
    var duplicate: String? = null

    var originalSource: String? = null
        set(value) {
            field = value
            innerImageOffsetX = 0f
            innerImageOffsetY = 0f
            innerImageScale = 1f
            innerImageRotation = 0f
        }

    var isVideo = false

    //1 means frame width/height
    var innerImageOffsetX = 0f
    var innerImageOffsetY = 0f
    var innerImageScale = 1f
    var innerImageRotation = 0f

    var demoOffsetX = 0f
    var demoOffsetY = 0f
    var demoScale = 1f

    var innerPivotX = 0.5f
    var innerPivotY = 0.5f

    var programCreator: ProgramCreator? = null

    @Serializable(with = CornersPositionSerializer::class)
    var cornerRadiusPosition: Int? = null

    var videoStartTimeMs: Int? = null
    var isLoopEnabled: Boolean? = null


    fun hasUserSource() = originalSource != null

    fun hasNoEditableProgram() = programCreator != null && !isEditable

    fun hasProgramOrVideo() = programCreator != null || (isVideo && originalSource != null)

    fun hasProgram() = programCreator != null

    fun hasEditableProgram() = programCreator != null && isEditable

    fun hasVideo() = isVideo && originalSource != null || programHasVideo()

    fun programHasVideo() =
        programCreator?.textures?.any { it.type == TextureCreator.Type.VIDEO || it.type == TextureCreator.Type.VIDEO_EDIT }
            ?: false


    override fun toString(): String {
        return "MediaImage(hasProgram = ${hasProgram()}, demoSource=$demoSource, borderWidth=$borderWidth, isEditable=$isEditable, duplicate=$duplicate, id=$id, originalSource=$originalSource, isVideo=$isVideo, innerImageOffsetX=$innerImageOffsetX, innerImageOffsetY=$innerImageOffsetY, innerImageScale=$innerImageScale)"
    }

    fun getVideoTimeOffsetUs(): Long = videoStartTimeMs?.let { it * 1000L } ?: 0L
}

const val BORDER_STYLE_INSIDE = "inside"
const val BORDER_STYLE_OUTSIDE = "outside"

const val CORNER_RADIUS_ONLY_TOP = 1
const val CORNER_RADIUS_ONLY_BOTTOM = 2


@Serializable
@SerialName("lottie")
data class MediaLottie(
    @Required
    var originalSource: String,
    var isLoopEnabled: Boolean? = null,
    @Serializable(with = LayoutPositionSerializer::class)
    override var layoutPosition: LayoutPosition, override var id: String? = null,
    override var translationX: Float = 0f,
    override var translationY: Float = 0f, override var rotation: Float = 0f,
    @Serializable(with = ColorSerializer::class)
    override var backgroundColor: Int = 0, override var textureIndex: Int? = null,
    @Serializable(with = MinDurationSerializer::class)
    override var minDuration: Int = 0,
    override var startFrame: Int = 0,
    override var delayBeforeEnd: Int = 0,
    override var animatorsIn: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsOut: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsAll: MutableList<InspAnimator> = Collections.emptyList(),
    override var loopedAnimationInterval: Int? = null, override var canMoveY: Boolean? = null,
    override var canMoveX: Boolean? = null, override var isMovable: Boolean? = null

) : Media()


@Serializable
@SerialName("path")
data class MediaPath(
    @Serializable(with = ColorSerializer::class)
    var color: Int? = null,
    var paintStyle: Paint.Style = Paint.Style.STROKE,
    val strokeWidth: String? = null,
    val strokeCap: String? = null,
    val movementsConnected: Boolean = false,
    var gradient: PaletteLinearGradient? = null,

    @Serializable(with = InterpolatorSerializer::class)
    var movementsInterpolator: InspInterpolator? = null,
    val movements: List<PathMovement> = listOf(),

    @Serializable(with = LayoutPositionSerializer::class)
    override var layoutPosition: LayoutPosition, override var id: String? = null, override var translationX: Float = 0f,
    override var translationY: Float = 0f, override var rotation: Float = 0f,
    @Serializable(with = ColorSerializer::class)
    override var backgroundColor: Int = 0, override var textureIndex: Int? = null,
    @Serializable(with = MinDurationSerializer::class)
    override var minDuration: Int = 0, override var startFrame: Int = 0, override var delayBeforeEnd: Int = 0,
    override var animatorsIn: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsOut: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsAll: MutableList<InspAnimator> = Collections.emptyList(),
    override var loopedAnimationInterval: Int? = null, override var canMoveY: Boolean? = null,
    override var canMoveX: Boolean? = null, override var isMovable: Boolean? = null
) : Media()

@Serializable
@SerialName("text")
class MediaText(
    @Serializable(with = LayoutPositionSerializer::class)
    override var layoutPosition: LayoutPosition, override var id: String? = null, override var translationX: Float = 0f,
    override var translationY: Float = 0f, override var rotation: Float = 0f,
    @Serializable(with = ColorSerializer::class)
    override var backgroundColor: Int = 0, override var textureIndex: Int? = null,
    @Serializable(with = MinDurationSerializer::class)
    override var minDuration: Int = 0,
    override var startFrame: Int = 0,
    override var delayBeforeEnd: Int = 0,
    override var animatorsIn: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsOut: MutableList<InspAnimator> = Collections.emptyList(),
    override var animatorsAll: MutableList<InspAnimator> = Collections.emptyList(),
    override var loopedAnimationInterval: Int? = null, override var canMoveY: Boolean? = null,
    override var canMoveX: Boolean? = null
) :
    Media() {

    var text: String = ""
    var textSize: String = "0"
    var forPremium = false
    var lineSpacing: Float = 1f
    var letterSpacing: Float = 0f

    @Serializable(with = FontDataSerializer::class)
    var font: FontData? = null

    @Serializable(with = GravitySerializer::class)
    var innerGravity: Int = Gravity.LEFT

    @Serializable(with = ColorSerializer::class)
    var textColor: Int = Color.BLACK

    @Serializable(with = TextAnimationParamsSerializer::class)
    var animationParamIn: TextAnimationParams? = null

    @Serializable(with = TextAnimationParamsSerializer::class)
    var animationParamOut: TextAnimationParams? = null

    var strokeWidth: Float? = null
    var paintStyle: Paint.Style? = null

    var shadowOffset: Float? = null
    var shadowColors: List<@Serializable(with = ColorSerializer::class) Int>? = null

    var porterDuffMode: PorterDuff.Mode? = null
    var blurFilter = false
    var dependsOnParent: Boolean = false

    var backgroundGradient: PaletteLinearGradient? = null
    var textGradient: PaletteLinearGradient? = null

    var textShadowDx: Float? = null
    var textShadowDy: Float? = null

    @Serializable(with = ColorSerializer::class)
    var textShadowColor: Int? = null
    var textShadowBlurRadius: Float? = null

    //those margins are calculated from x * fontHeight
    var backgroundMarginLeft = 0f
    var backgroundMarginTop = 0f
    var backgroundMarginRight = 0f
    var backgroundMarginBottom = 0f

    override var isMovable: Boolean?
        get() = true
        set(value) {}


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaText

        if (text != other.text) return false
        if (textSize != other.textSize) return false
        if (forPremium != other.forPremium) return false
        if (lineSpacing != other.lineSpacing) return false
        if (letterSpacing != other.letterSpacing) return false
        if (font != other.font) return false
        if (innerGravity != other.innerGravity) return false
        if (textColor != other.textColor) return false
        if (strokeWidth != other.strokeWidth) return false
        if (paintStyle != other.paintStyle) return false
        if (shadowOffset != other.shadowOffset) return false
        if (shadowColors != null) {
            if (other.shadowColors == null) return false
            if (shadowColors != other.shadowColors) return false
        } else if (other.shadowColors != null) return false
        if (porterDuffMode != other.porterDuffMode) return false
        if (blurFilter != other.blurFilter) return false
        if (dependsOnParent != other.dependsOnParent) return false
        if (backgroundGradient != other.backgroundGradient) return false
        if (textGradient != other.textGradient) return false
        if (textShadowDx != other.textShadowDx) return false
        if (textShadowDy != other.textShadowDy) return false
        if (textShadowColor != other.textShadowColor) return false
        if (textShadowBlurRadius != other.textShadowBlurRadius) return false
        if (backgroundMarginLeft != other.backgroundMarginLeft) return false
        if (backgroundMarginTop != other.backgroundMarginTop) return false
        if (backgroundMarginRight != other.backgroundMarginRight) return false
        if (backgroundMarginBottom != other.backgroundMarginBottom) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + textSize.hashCode()
        result = 31 * result + forPremium.hashCode()
        result = 31 * result + lineSpacing.hashCode()
        result = 31 * result + letterSpacing.hashCode()
        result = 31 * result + (font?.hashCode() ?: 0)
        result = 31 * result + innerGravity
        result = 31 * result + textColor
        result = 31 * result + (strokeWidth?.hashCode() ?: 0)
        result = 31 * result + (paintStyle?.hashCode() ?: 0)
        result = 31 * result + (shadowOffset?.hashCode() ?: 0)
        result = 31 * result + (shadowColors?.hashCode() ?: 0)
        result = 31 * result + (porterDuffMode?.hashCode() ?: 0)
        result = 31 * result + blurFilter.hashCode()
        result = 31 * result + dependsOnParent.hashCode()
        result = 31 * result + (backgroundGradient?.hashCode() ?: 0)
        result = 31 * result + (textGradient?.hashCode() ?: 0)
        result = 31 * result + (textShadowDx?.hashCode() ?: 0)
        result = 31 * result + (textShadowDy?.hashCode() ?: 0)
        result = 31 * result + (textShadowColor ?: 0)
        result = 31 * result + (textShadowBlurRadius?.hashCode() ?: 0)
        result = 31 * result + backgroundMarginLeft.hashCode()
        result = 31 * result + backgroundMarginTop.hashCode()
        result = 31 * result + backgroundMarginRight.hashCode()
        result = 31 * result + backgroundMarginBottom.hashCode()
        return result
    }
}