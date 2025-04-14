package com.naulian.parallax

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

//Linear Mapping
fun map(x: Float, xMin: Float, xMax: Float, yMin: Float, yMax: Float): Float {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedMap(x: Float, xMin: Float, xMax: Float, yMin: Float, yMax: Float): Float {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return map(x, xMin, xMax, yMin, yMax)
}

fun closedMap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return map(x.toFloat(), xMin.toFloat(), xMax.toFloat(), yMin, yMax)
}

fun map(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedMap(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return map(x, xMin, xMax, yMin, yMax)
}

fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

fun xOffSet(x: Int) = IntOffset(x, 0)
fun yOffSet(y: Int) = IntOffset(0, y)

fun xOffsetFrom(
    value: Float,
    start: Int,
    end: Int
): IntOffset {
    if (value > 1f) return IntOffset(x = end, y = 0)
    if (value < 0f) return IntOffset(x = start, y = 0)

    val x = map(value, 0f, 1f, start.toFloat(), end.toFloat()).toInt()
    return IntOffset(x = x, y = 0)
}

fun yOffsetFrom(
    value: Float,
    start: Int,
    end: Int
): IntOffset {
    if (value > 1f) return IntOffset(x = 0, y = end)
    if (value < 0f) return IntOffset(x = 0, y = start)

    val y = map(value, 0f, 1f, start.toFloat(), end.toFloat()).toInt()
    return IntOffset(x = 0, y = y)
}

data class ValueRange<N : Number>(
    val min: N,
    val max: N,
)

infix fun <N : Number> N.rangeTo(other: N) = ValueRange(this, other)

@Suppress("KotlinConstantConditions") //this bug is said to be fixed in the coming intellij update
fun Int.scale(scalar: Float): Int {
    if (this == 0 || scalar == 0f) return 0
    if (scalar == 1f) return this
    return (this * scalar).toInt()
}

fun Int.scale(percent: Int): Int {
    return scale((percent / 100f))
}

@Suppress("KotlinConstantConditions")
val Int.half
    get(): Int {
        if (this == 0) return 0
        return (this / 2f).toInt()
    }

@Composable
fun <T> rememberCalculation(calculation: () -> T) = remember {
    derivedStateOf(calculation)
}

@Composable
fun ParallaxState.rememberScrollOffset() = remember {
    derivedStateOf {
        (state.firstVisibleItemIndex * height) + state.firstVisibleItemScrollOffset
    }
}
