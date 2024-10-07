package com.naulian.parallax

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt


fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

fun xOffSet(x: Int) = IntOffset(x, 0)
fun yOffSet(y: Int) = IntOffset(0, y)

data class ValueRange<N : Number>(
    val min: N,
    val max: N,
)

infix fun <N : Number> N.into(other: N) = ValueRange(this, other)

fun ParallaxScope.smartLmap(
    offset: Int,
    index: Int,
    enterValues: ValueRange<Int>,
    exitValues: ValueRange<Int>? = null,
): Int {
    val exitXMin = (index * height) + height
    if (exitValues != null && offset >= exitXMin) {
        val xMax = ((index + 1) * height) + height
        return lmap(offset, exitXMin, xMax, exitValues.min, exitValues.max)
    }

    val xMin = (index * height)
    val xMax = (index * height) + height
    return lmap(offset, xMin, xMax, enterValues.min, enterValues.max)
}

fun ParallaxScope.lmapFloat(
    offset: Int,
    index: Int,
    animateEnter: Boolean = true,
    animateExit: Boolean = true,
): Float {
    return smartClosedLmap(
        offset = offset,
        index = index,
        enterValues = if (animateEnter) 0f into 1f else null,
        exitValues = if (animateExit) 1f into 0f else null
    )
}

fun ParallaxScope.smartClosedLmap(
    offset: Int,
    index: Int,
    enterValues: ValueRange<Float>?,
    exitValues: ValueRange<Float>? = null,
): Float {

    val exitXMin = (index * height) + height
    if (exitValues != null && offset > exitXMin) {
        val xMax = ((index + 1) * height) + height // when 0 -> height2 when 1 -> height3
        return closedLmap(offset, exitXMin, xMax, exitValues.min, exitValues.max)
    }

    if (enterValues != null) {
        val xMin = (index * height) // when 0 -> 0 when 1 -> height
        val xMax = exitXMin // when 0 -> height when 1 -> height2
        return closedLmap(offset, xMin, xMax, enterValues.min, enterValues.max)
    }

    return 0f
}

fun ParallaxScope.smartClosedLmap(
    offset: Int,
    index: Int,
    enterValues: ValueRange<Int>?,
    exitValues: ValueRange<Int>? = null,
): Int {

    val exitXMin = (index * height) + height
    if (exitValues != null && offset > exitXMin) {
        val xMax = ((index + 1) * height) + height // when 0 -> height2 when 1 -> height3
        return closedLmap(offset, exitXMin, xMax, exitValues.min, exitValues.max)
    }

    if (enterValues != null) {
        val xMin = (index * height) // when 0 -> 0 when 1 -> height
        val xMax = exitXMin // when 0 -> height when 1 -> height2
        return closedLmap(offset, xMin, xMax, enterValues.min, enterValues.max)
    }

    return 0
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

data class OffsetCalculationSpec(
    val offset: Int,
    val index: Int,
    val animateFrom: Int,
    val animateTo: Int,
)

@Composable
fun ParallaxScope.calculateYOffset(dataProvider: () -> OffsetCalculationSpec) = remember {
    derivedStateOf {
        val data = dataProvider()
        val y = smartLmap(
            data.offset,
            data.index,
            data.animateFrom into data.animateTo
        )
        yOffSet(y)
    }
}