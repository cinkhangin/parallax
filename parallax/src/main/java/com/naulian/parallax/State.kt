package com.naulian.parallax

import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

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
    val screenCount : Int,
    val state: LazyListState,
    val height: Int, //pixel
    val heightDp : Dp,
    val maxHeight : Int
)

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