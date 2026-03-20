package com.ckgin.parallax

import android.R.attr.end
import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

val String.asParagraph get() = trimIndent().replace("\n", " ")

@SuppressLint("ComposableNaming")
@Composable
fun ComponentActivity.hideSystemBars(){
    val view = LocalView.current
    WindowCompat.getInsetsController(window, view).apply {
        hide(WindowInsetsCompat.Type.systemBars())
        systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Modifier.slideX(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    from: Int = 0, to: Int = 0
) = this.offset {
    xOffsetFrom(progress, from, to)
}

fun Modifier.slideY(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    from: Int = 0, to: Int = 0
) = this.offset {
    yOffsetFrom(progress, from, to)
}

fun Modifier.slide(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    fromX: Int = 0, toX: Int = 0, fromY: Int = 0, toY: Int = 0
) = when {
    fromX == toX -> slideY(progress, fromY, toY)
    fromY == toY -> slideX(progress, fromX, toX)
    else -> this.offset {
        val x = xOffsetFrom(progress, fromX, toX).x
        val y = yOffsetFrom(progress, fromY, toY).y
        IntOffset(x = x, y = y)
    }
}