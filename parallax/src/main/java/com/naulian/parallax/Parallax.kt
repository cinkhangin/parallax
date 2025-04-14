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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun Parallax(
    screenCount: Int,
    scrollItemContent : LazyListScope.(ParallaxState) -> Unit = {state ->
        items(screenCount) {
            Box(modifier = Modifier.fillMaxWidth().height(state.heightDp))
        }
    },
    content: @Composable ParallaxState.() -> Unit
) {
    ParallaxLayout(screenCount = screenCount) {
        val snappingLayout = remember(state) {
            SnapLayoutInfoProvider(state, SnapPosition.Center)
        }
        val snapFlingBehavior = rememberParallaxFlingBehavior(snappingLayout)

        Box(modifier = Modifier.fillMaxSize()) {
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
}

@Composable
fun ParallaxLayout(screenCount : Int, content: @Composable ParallaxState.() -> Unit) {
    val scrollSate = rememberParallaxColumnState()

    val heightPx = calculatedHeightPx
    val heightDp = calculatedHeightDp

    val parallaxState by remember {
        mutableStateOf(
            ParallaxState(
                screenCount = screenCount,
                state = scrollSate,
                height = heightPx,
                heightDp = heightDp
            )
        )
    }

    parallaxState.content()
}
