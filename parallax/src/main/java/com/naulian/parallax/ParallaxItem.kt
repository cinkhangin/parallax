package com.naulian.parallax

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class ParallaxProgress(
    val index : Int,
    val enterProgress: Float,
    val exitProgress: Float,
    val scrollDownOffset: Int,
    val scrollUpOffset: Int,

    val scrollProgress : Float,
    val startOffset : Int,
    val endOffset : Int
)

@Composable
fun ParallaxState.ParallaxItem(
    screenIndex: Int,
    modifier: Modifier = Modifier,
    contentAlignment : Alignment = Alignment.TopStart,
    content: @Composable ParallaxState.(ParallaxProgress) -> Unit,
) {
    val maxOffset = height * screenCount
    val scrollOffset by rememberScrollOffset()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        content(
            ParallaxProgress(
                index = screenIndex,
                enterProgress = closedMap(
                    scrollOffset,
                    (screenIndex - 1) * height,
                    screenIndex * height,
                    0f,
                    1f
                ),
                exitProgress = closedMap(
                    scrollOffset,
                    screenIndex * height,
                    (screenIndex + 1) * height,
                    0f,
                    1f
                ),
                scrollDownOffset = scrollOffset,
                scrollUpOffset = maxOffset - scrollOffset,
                scrollProgress = closedMap(
                    scrollOffset,
                    0,
                    maxHeight,
                    0f,
                    1f
                ),
                startOffset = screenIndex  * height,
                endOffset = (screenIndex + 1) * height
            )
        )
    }
}

@Composable
fun ParallaxState.ParallaxItem(
    content: @Composable ParallaxState.(ParallaxProgress) -> Unit,
) {
    val maxOffset = (height * (screenCount - 1))
    val scrollOffset by rememberScrollOffset()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp)
    ) {
        content(
            ParallaxProgress(
                index = 0,
                enterProgress = closedMap(scrollOffset, 0, maxOffset, 0f, 1f),
                exitProgress = closedMap(scrollOffset, maxOffset, 0, 0f, 1f),
                scrollDownOffset = scrollOffset,
                scrollUpOffset = maxOffset - scrollOffset,
                scrollProgress = closedMap(scrollOffset, 0, maxHeight, 0f, 1f),
                startOffset =0,
                endOffset = maxHeight
            )
        )
    }
}