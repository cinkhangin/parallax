package com.naulian.parallax

import androidx.compose.ui.unit.IntOffset

enum class SlideDirection {
    LEFT, RIGHT, UP, DOWN
}

fun ParallaxScope.calculateSlideOneWay(
    offset: Int,
    index: Int,
    targetValues: ValueRange<Int>,
    direction: SlideDirection = SlideDirection.UP,
): IntOffset {
    val newOffSet = smartLmap(
        offset = offset,
        index = index,
        enterValues = targetValues
    )

    return when (direction) {
        SlideDirection.UP -> yOffSet(newOffSet)
        SlideDirection.DOWN -> yOffSet(-newOffSet)
        SlideDirection.LEFT -> xOffSet(-newOffSet)
        SlideDirection.RIGHT -> xOffSet(newOffSet)
    }
}

fun ParallaxScope.calculateSlideOnTwoWay(
    offset: Int,
    index: Int,
    enterOffset: ValueRange<Int>,
    exitOffset: ValueRange<Int>,
    direction: SlideDirection = SlideDirection.UP,
): IntOffset {
    val newOffSet = smartLmap(
        offset = offset,
        index = index,
        enterValues = enterOffset,
        exitValues = exitOffset
    )

    return when (direction) {
        SlideDirection.UP -> yOffSet(newOffSet)
        SlideDirection.DOWN -> yOffSet(-newOffSet)
        SlideDirection.LEFT -> xOffSet(-newOffSet)
        SlideDirection.RIGHT -> xOffSet(newOffSet)
    }
}