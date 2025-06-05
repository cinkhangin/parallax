package com.naulian.parallax

import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity


@Composable
fun Parallax(
    screenCount: Int,
    modifier: Modifier = Modifier,
    scrollItemContent: LazyListScope.(ParallaxState) -> Unit = { state ->
        items(screenCount) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(state.heightDp)
            )
        }
    },
    content: @Composable ParallaxState.() -> Unit
) {
    ParallaxLayout(
        screenCount = screenCount,
        modifier = modifier
    ) {
        val snappingLayout = remember(state) {
            SnapLayoutInfoProvider(state, SnapPosition.Center)
        }
        val snapFlingBehavior = rememberParallaxFlingBehavior(snappingLayout)

        content()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = state,
            flingBehavior = snapFlingBehavior
        ) {
            scrollItemContent(this@ParallaxLayout)
        }
    }
}

@Composable
internal fun ParallaxLayout(
    screenCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable ParallaxState.() -> Unit
) {
    var heightPx by remember { mutableIntStateOf(0) }
    val heightDp = with(LocalDensity.current) { heightPx.toDp() }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            heightPx = coordinates.size.height
        }
    ) {
        if (heightPx > 0) {
            val scrollSate = rememberParallaxColumnState()
            val parallaxState by remember {
                mutableStateOf(
                    ParallaxState(
                        screenCount = screenCount,
                        state = scrollSate,
                        height = heightPx,
                        heightDp = heightDp,
                        maxHeight = heightPx * screenCount
                    )
                )
            }

            parallaxState.content()
        }
    }
}
