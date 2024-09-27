package com.naulian.parallax

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

//Linear Mapping
fun lmap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedLmap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return lmap(x, xMin, xMax, yMin, yMax)
}

fun lmap(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedLmap(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return lmap(x, xMin, xMax, yMin, yMax)
}

fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

fun xOffSet(x: Int) = IntOffset(x, 0)
fun yOffSet(y: Int) = IntOffset(0, y)

/*
val slide2Alpha by remember {
    derivedStateOf {
        if (offSet <= height * 3) closedLmap(offSet, height * 2, height * 3, 0f, 1f)
        else closedLmap(offSet, height * 3, height * 4, 1f, 0f)
    }
}

val slide2YBack by remember {
    derivedStateOf {
        if (offSet <= height * 3) lmap(offSet, height * 2, height * 3, -1600, 0)
        else lmap(offSet, height * 3, height * 4, 0, 1600)
    }
}

val slide2Scale by remember {
    derivedStateOf {
        if (offSet <= height * 3)
            closedLmap(offSet, height * 2, height * 3, 0f, 1f)
        else closedLmap(offSet, height * 3, height * 4, 1f, 0f)
    }
}*/

data class ValueRange<N :Number>(
    val min: N,
    val max: N,
)

infix fun <N : Number> N.rangeTo(other: N) = ValueRange(this, other)

fun ParallaxState.smartLmap(
    offset: Int,
    index: Int,
    outRange: ValueRange<Int>,
    exitOutRange: ValueRange<Int>? = null,
): Int {
    val exitXMin = (index * height) + height
    if (exitOutRange != null && offset > exitXMin) {
        val xMax = ((index + 1) * height) + height
        return lmap(offset, exitXMin, xMax, exitOutRange.min, exitOutRange.max)
    }

    val xMin = (index * height)
    val xMax = (index * height) + height
    return lmap(offset, xMin, xMax, outRange.min, outRange.max)
}

fun Int.scale(scalar: Float): Int {
    if (this == 0 || scalar == 0f) return 0
    if (scalar == 1f) return this
    return (this * scalar).toInt()
}

fun Int.scale(percent: Int): Int {
    return scale((percent / 100f))
}

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
fun rememberScrollOffset(state: LazyListState, itemHeight: Int) = remember {
    derivedStateOf {
        (state.firstVisibleItemIndex * itemHeight) + state.firstVisibleItemScrollOffset
    }
}
