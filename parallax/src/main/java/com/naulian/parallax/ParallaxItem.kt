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
    val enterProgress: Float,
    val exitProgress: Float,
    val scrollDownOffset: Int,
    val scrollUpOffset: Int
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
                scrollUpOffset = maxOffset - scrollOffset
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
                enterProgress = closedMap(scrollOffset, 0, maxOffset, 0f, 1f),
                exitProgress = closedMap(scrollOffset, maxOffset, 0, 0f, 1f),
                scrollDownOffset = scrollOffset,
                scrollUpOffset = maxOffset - scrollOffset
            )
        )
    }
}