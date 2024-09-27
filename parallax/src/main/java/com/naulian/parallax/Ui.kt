package com.naulian.parallax

import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity


fun LazyListScope.parallax(
    modifier: Modifier = Modifier,
    background: Color,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    item {
        Box(
            modifier = modifier
                .fillMaxSize()
                .height(calculatedHeightDp)
                .background(background),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}

@Composable
fun rememberParallaxColumnState(
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0,
): LazyListState {
    return rememberSaveable(saver = LazyListState.Saver) {
        LazyListState(
            initialFirstVisibleItemIndex,
            initialFirstVisibleItemScrollOffset
        )
    }
}

data class ParallaxState(
    val state: LazyListState,
    val height: Int,
)

@Composable
fun ParallaxLayout(content: @Composable ParallaxState.() -> Unit) {
    MaterialTheme {
        val scrollSate = rememberParallaxColumnState()
        val heightPx = calculatedHeightPx
        val parallaxState by remember {
            mutableStateOf(
                ParallaxState(
                    state = scrollSate,
                    height = heightPx,
                )
            )
        }

        parallaxState.content()
    }
}


@Composable
fun ParallaxState.ParallaxColumn(content: LazyListScope.() -> Unit) {
    val snappingLayout = remember(state) {
        SnapLayoutInfoProvider(state, SnapPosition.Center)
    }
    val snapFlingBehavior = rememberParallaxFlingBehavior(snappingLayout)

    LazyColumn(
        state = state,
        flingBehavior = snapFlingBehavior,
        content = content
    )
}

@Composable
fun rememberParallaxFlingBehavior(
    snapLayoutInfoProvider: SnapLayoutInfoProvider,
): TargetedFlingBehavior {
    val density = LocalDensity.current
    val highVelocityApproachSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay()
    return remember(
        snapLayoutInfoProvider,
        highVelocityApproachSpec,
        density
    ) {
        snapFlingBehavior(
            snapLayoutInfoProvider = snapLayoutInfoProvider,
            decayAnimationSpec = highVelocityApproachSpec,
            snapAnimationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}