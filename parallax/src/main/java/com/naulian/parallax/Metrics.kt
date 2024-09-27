package com.naulian.parallax

import android.os.Build
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor

val ComponentActivity.deviceHeightPx
    @Composable get(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return windowManager.currentWindowMetrics.bounds.height()
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

val calculatedHeightPx
    @Composable get() : Int {
        val px = with(LocalDensity.current) {
            calculatedHeightDp.value * density
        }
        return ceil(px).toInt()
    }

val calculatedHeightDp
    @Composable get() : Dp {
        val context = (LocalContext.current as ComponentActivity)
        val dp = with(LocalDensity.current) {
            context.deviceHeightPx / density
        }
        return floor(dp).dp
    }