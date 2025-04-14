package com.naulian.parallax

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


fun LazyListScope.parallax(
    modifier: Modifier = Modifier,
    background: Color = Color.White,
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
fun VectorImage(modifier: Modifier = Modifier, @DrawableRes drawable: Int) {
    Image(
        modifier = modifier
            .scale(1.32f),
        painter = painterResource(drawable),
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}