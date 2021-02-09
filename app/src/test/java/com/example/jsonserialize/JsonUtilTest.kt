package com.example.jsonserialize

import com.example.jsonserialize.animator.InspAnimator
import com.example.jsonserialize.media.*
import com.example.jsonserialize.serialization.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JsonUnitTest {

    @Test
    fun testInterpolator() {
        val testJson = "elegantlySlowOut"
        val interpolator = json.decodeFromString(InterpolatorSerializer, testJson)
        print(interpolator::class)
    }

    @Test
    fun testAnimator() {
        val testJson = "{\n" +
                "            \"type\": \"move_to_y\",\n" +
                "            \"from\": 1,\n" +
                "            \"to\": 0,\n" +
                "            \"durationMillis\": 2000,\n" +
                "            \"startTimeMillis\": 1000,\n" +
                "            \"interpolator\": \"elegantlySlowOut\"\n" +
                "          }\n"

        val animator = json.decodeFromString<InspAnimator>(testJson)

        println(animator)

        val backToJson = json.encodeToString(animator)

        println(backToJson)
    }

    @Test
    fun testPalette() {
        val testJson = "{\n" +
                "    \"choices\": [\n" +
                "      {\n" +
                "        \"color\": \"#DFC9BD\",\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"elementBackgroundColor\",\n" +
                "            \"id\": \"pathMask\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"image\",\n" +
                "            \"id\": \"imageMask\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"textColor\",\n" +
                "            \"id\": \"textLinkBottom\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"textColor\",\n" +
                "            \"id\": \"textTitle\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"textColor\",\n" +
                "            \"id\": \"insideText\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"elements\": [\n" +
                "          {\n" +
                "            \"type\": \"background\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"elementBackgroundColor\",\n" +
                "            \"id\": \"insideText\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }"

        val palette = json.decodeFromString<Palette>(testJson)

        println(palette)

        val backToJson = json.encodeToString(palette)

        println(backToJson)
    }

    @Test
    fun testLayoutPosition() {

        val testJson = "{" +
                "\"width\": \"1w\",\n" +
                "      \"height\": \"449/1920h\",\n" +
                "      \"anchorY\": \"bottom\",\n" +
                "      \"anchorX\": \"center\",\n" +
                "      \"y\": \"0.25h\"" +
                "}"

        val layoutPosition = json.decodeFromString(LayoutPositionSerializer, testJson)

        println(layoutPosition)

        val backToJson = json.encodeToString(LayoutPositionSerializer, layoutPosition)

        println(backToJson)
    }

    @Test
    fun testLayoutPosition2() {

        val testJson = "{" +
                "\"width\": \"1w\",\n" +
                "      \"height\": \"449/1920h\",\n" +
                "      \"y\": \"0.25h\"" +
                "}"

        val layoutPosition = json.decodeFromString(LayoutPositionSerializer, testJson)

        println(layoutPosition)

        val backToJson = json.encodeToString(LayoutPositionSerializer, layoutPosition)

        println(backToJson)
    }

    @Test
    fun testAnimParam() {

        val testJson = "{\n" +
                "        \"wordInterpolator\": {\n" +
                "          \"type\": \"accelerate\",\n" +
                "          \"factor\": 1.2\n" +
                "        },\n" +
                "        \"textAnimators\": [\n" +
                "          {\n" +
                "            \"duration\": 8,\n" +
                "            \"interpolator\": \"flatIn25expOut\",\n" +
                "            \"type\": \"fade\",\n" +
                "            \"from\": 0,\n" +
                "            \"to\": 1\n" +
                "          },\n" +
                "          {\n" +
                "            \"duration\": 8,\n" +
                "            \"interpolator\": \"flatIn25expOut\",\n" +
                "            \"type\": \"move\",\n" +
                "            \"fromX\": 0,\n" +
                "            \"toX\": 0,\n" +
                "            \"fromY\": 1,\n" +
                "            \"toY\": 0,\n" +
                "            \"relativeToParent\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"charDelay\": 2,\n" +
                "        \"wordDelayMillis\": 150,\n" +
                "        \"lineDelayMillis\": 150\n" +
                "      }\n"

        val obj = json.decodeFromString(TextAnimationParamsSerializer, testJson)

        println(obj)

        val backToJson = json.encodeToString(TextAnimationParamsSerializer, obj)

        println(backToJson)
    }

    @Test
    fun testProgramCreator() {

        val testJson = "{\n" +
                "        \"vertexShader\": \"file:///android_asset/template-resources/common_shaders/vertex_2items_shader.glsl\",\n" +
                "        \"fragmentShader\": \"file:///android_asset/template-resources/common_shaders/fragment_2blend_add_shader.glsl\",\n" +
                "        \"textures\": [\n" +
                "          {\n" +
                "            \"name\": 0,\n" +
                "            \"type\": \"template\",\n" +
                "            \"matrices\": [\n" +
                "              {\n" +
                "                \"name\": 0,\n" +
                "                \"type\": \"aspect-ratio\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"video\",\n" +
                "            \"name\": 1,\n" +
                "            \"source\": \"file:///android_asset/template-resources/christmas/Ch1FestiveMood/mask.mp4\",\n" +
                "            \"isLoopEnabled\": true,\n" +
                "            \"matrices\": [\n" +
                "              {\n" +
                "                \"name\": 1,\n" +
                "                \"type\": \"aspect-ratio\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n"

        val obj = json.decodeFromString<ProgramCreator>(testJson)

        println(obj)

        val backToJson = json.encodeToString(obj)

        println(backToJson)
    }

    @Test
    fun testMediaText() {

        val testJson = "{\n" +
                "      \"width\": \"wrap_content\",\n" +
                "      \"height\": \"wrap_content\",\n" +
                "      \"anchorY\": \"center\",\n" +
                "      \"anchorX\": \"center\",\n" +
                "      \"type\": \"text\",\n" +
                "      \"innerGravity\": \"center\",\n" +
                "      \"text\": \"try the best\",\n" +
                "      \"font\": {\n" +
                "        \"fontPath\": \"garamond\"\n" +
                "      },\n" +
                "      \"translationY\": 0.3531896,\n" +
                "      \"textSize\": \"0.055913605m\",\n" +
                "      \"minDuration\": \"as_template\",\n" +
                "      \"textColor\": \"#000000\",\n" +
                "      \"backgroundMarginLeft\": 0.2,\n" +
                "      \"backgroundMarginRight\": 0.2,\n" +
                "      \"startFrame\": 40,\n" +
                "      \"animationParamIn\": {\n" +
                "        \"wordDelay\": 6,\n" +
                "        \"lineDelay\": 6,\n" +
                "        \"textAnimators\": [\n" +
                "          {\n" +
                "            \"type\": \"fade\",\n" +
                "            \"from\": 0,\n" +
                "            \"to\": 1,\n" +
                "            \"duration\": 10\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"move_to_y\",\n" +
                "            \"from\": 0.8,\n" +
                "            \"to\": 0,\n" +
                "            \"duration\": 10,\n" +
                "            \"interpolator\": \"flatIn25expOut\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }"

        val obj: MediaText = json.decodeFromString(MediaTextSerializer, testJson)

        println(obj)

        val backToJson = json.encodeToString(obj)

        println(backToJson)
    }

    @Test
    fun testMediaPath() {

        val testJson = "{\n" +
                "      \"type\": \"path\",\n" +
                "      \"width\": \"461/540w\",\n" +
                "      \"height\": \"273/320h\",\n" +
                "      \"color\": \"#CCffffff\",\n" +
                "      \"anchorX\": \"center\",\n" +
                "      \"anchorY\": \"center\",\n" +
                "      \"paintStyle\": \"STROKE\",\n" +
                "      \"strokeWidth\": \"0.03m\",\n" +
                "      \"id\": \"path\",\n" +
                "      \"animatorsIn\": [\n" +
                "        {\n" +
                "          \"type\": \"fade\",\n" +
                "          \"duration\": 0,\n" +
                "          \"from\": 0.8,\n" +
                "          \"to\": 0.8\n" +
                "        }\n" +
                "      ],\n" +
                "      \"movements\": [\n" +
                "        {\n" +
                "          \"type\": \"line\",\n" +
                "          \"fromX\": 1,\n" +
                "          \"toX\": 0,\n" +
                "          \"startFrame\": 0,\n" +
                "          \"duration\": 23\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"line\",\n" +
                "          \"fromY\": 0,\n" +
                "          \"toY\": 1,\n" +
                "          \"startFrame\": 23,\n" +
                "          \"duration\": 23\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"line\",\n" +
                "          \"fromX\": 0,\n" +
                "          \"toX\": 1,\n" +
                "          \"fromY\": 1,\n" +
                "          \"toY\": 1,\n" +
                "          \"startFrame\": 46,\n" +
                "          \"duration\": 23\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"line\",\n" +
                "          \"fromX\": 1,\n" +
                "          \"toX\": 1,\n" +
                "          \"fromY\": 1,\n" +
                "          \"toY\": 0,\n" +
                "          \"startFrame\": 78,\n" +
                "          \"duration\": 36,\n" +
                "          \"interpolator\": \"elegantlySlowOut\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }"

        val obj: MediaPath = json.decodeFromString(MediaPathSerializer, testJson)

        println(obj)

        val backToJson = json.encodeToString(obj)

        println(backToJson)
    }
    @Test
    fun testMediaGroup() {

        val testJson = "{\n" +
                "      \"textureIndex\": 0,\n" +
                "      \"type\": \"group\",\n" +
                "      \"id\": \"groupImages\",\n" +
                "      \"width\": \"match_parent\",\n" +
                "      \"height\": \"match_parent\",\n" +
                "      \"medias\": [\n" +
                "        {\n" +
                "          \"textureIndex\": 0,\n" +
                "          \"type\": \"image\",\n" +
                "          \"demoSource\": \"https://images.unsplash.com/photo-1531853121101-cb94c8ed218d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80\",\n" +
                "          \"width\": \"840/1080w\",\n" +
                "          \"height\": \"840/1080w\",\n" +
                "          \"x\": \"39\",\n" +
                "          \"y\": \"86\",\n" +
                "          \"anchorX\": \"end\",\n" +
                "          \"anchorY\": \"bottom\",\n" +
                "          \"startFrame\": 40,\n" +
                "          \"animatorsIn\": [\n" +
                "            {\n" +
                "              \"type\": \"scale_inner\",\n" +
                "              \"duration\": 50,\n" +
                "              \"from\": 1,\n" +
                "              \"to\": 1.1\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"textureIndex\": 0,\n" +
                "          \"type\": \"image\",\n" +
                "          \"demoSource\": \"https://images.unsplash.com/photo-1587393795320-6e43b260ecd0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=600&q=80\",\n" +
                "          \"width\": \"617/1080w\",\n" +
                "          \"height\": \"706/1080w\",\n" +
                "          \"x\": \"94\",\n" +
                "          \"y\": \"338\",\n" +
                "          \"startFrame\": 20,\n" +
                "          \"animatorsIn\": [\n" +
                "            {\n" +
                "              \"type\": \"scale_inner\",\n" +
                "              \"duration\": 50,\n" +
                "              \"from\": 1,\n" +
                "              \"to\": 1.08\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }"

        val obj = json.decodeFromString(MediaGroupSerializer, testJson)

        println(obj)

        val backToJson = json.encodeToString(MediaGroupSerializer, obj)

        println(backToJson)

        val backToObj = json.decodeFromString(MediaGroupSerializer, backToJson)

        print(backToObj)
    }

    @Test
    fun testTemplate() {

        val testJson = "{\n" +
                "  \"preferredDuration\": 180,\n" +
                "  \"palette\": {\n" +
                "    \"backgroundImage\": \"https://images.unsplash.com/photo-1527954513726-611b208be16a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80\"\n" +
                "  },\n" +
                "  \"medias\": [\n" +
                "    {\n" +
                "      \"type\": \"text\",\n" +
                "      \"text\": \"Create text styles\",\n" +
                "      \"anchorX\": \"center\",\n" +
                "      \"innerGravity\": \"start\",\n" +
                "      \"width\": \"0.691w\",\n" +
                "      \"height\": \"0.172h\",\n" +
                "      \"translationX\": -0.0343,\n" +
                "      \"translationY\": 0.0463,\n" +
                "      \"startFrame\": 5,\n" +
                "      \"minDuration\": \"as_template\",\n" +
                "      \"font\": {\n" +
                "        \"fontPath\": \"spectral\",\n" +
                "        \"fontStyle\": \"bold\"\n" +
                "      },\n" +
                "      \"textSize\": \"0.047m\",\n" +
                "      \"textColor\": \"#ffffff\",\n" +
                "      \"lineSpacing\": 0.7,\n" +
                "      \"animationParamIn\": {\n" +
                "        \"wordDelay\": 5,\n" +
                "        \"lineDelay\": 5,\n" +
                "        \"textAnimators\": [\n" +
                "          {\n" +
                "            \"type\": \"fade\",\n" +
                "            \"from\": 0,\n" +
                "            \"to\": 1,\n" +
                "            \"duration\": 10,\n" +
                "            \"interpolator\": \"elegantlySlowOut\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"move_to_y\",\n" +
                "            \"from\": 1,\n" +
                "            \"to\": 0,\n" +
                "            \"duration\": 10,\n" +
                "            \"interpolator\": \"elegantlySlowOut\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        val template = json.decodeFromString<Template>(testJson)

        println(template)

        val backToJson = json.encodeToString(template)

        println(backToJson)

        val backToTemplate = json.decodeFromString<Template>(backToJson)

        print(backToTemplate)
    }
}
