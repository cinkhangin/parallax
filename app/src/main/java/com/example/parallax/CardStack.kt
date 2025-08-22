package com.example.parallax

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.White
import com.naulian.parallax.Parallax
import com.naulian.parallax.ParallaxItem
import com.naulian.parallax.closedMap
import com.naulian.parallax.yOffSet

data class CardModel(
    val color: Color = Color(0xFFFFFFFF),
    val id: Int = 0
)

val cardList = listOf(
    CardModel(color = Color(0xFFB0BEC5), id = 0),
    CardModel(color = Color(0xFFC8E6C9), id = 1),
    CardModel(color = Color(0xFFFFF9C4), id = 2),
    CardModel(color = Color(0xFFFFCCBC), id = 3),
    CardModel(color = Color(0xFFD1C4E9), id = 4),
    CardModel(color = Color(0xFFB2DFDB), id = 5),
    CardModel(color = Color(0xFFFFCDD2), id = 6),
    CardModel(color = Color(0xFFF0F4C3), id = 7),
    CardModel(color = Color(0xFFD7CCC8), id = 8),
    CardModel(color = Color(0xFFCFD8DC), id = 9)
)

@SuppressLint("ComposableNaming")
@Composable
fun Modifier.If(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) this then Modifier.modifier() else this
}

@Composable
fun CardStack(modifier: Modifier = Modifier) {
    Parallax(screenCount = 10, modifier = modifier.padding(top = 24.dp)) {
        cardList.forEachIndexed { index, card ->

            var selectedIndex by remember { mutableIntStateOf(-1) }

            val extraOffset by animateIntAsState(
                targetValue = when{
                    selectedIndex == -1 -> 0
                    selectedIndex < index -> 100
                    else -> 0
                }
            )

            ParallaxItem(
                modifier = Modifier.fillMaxSize(),
                screenIndex = index,
            ) { progress ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                        .offset {
                            val newY = closedMap(
                                x = progress.scrollDownOffset,
                                xMin = 0,
                                xMax = maxHeight,
                                yMin = 0,
                                yMax = 1000
                            )
                            yOffSet(y = (300 * progress.index) -newY + extraOffset)
                        }
                        .background(card.color, CircleShape)
                        .clickable{
                            if(selectedIndex == index) {
                                selectedIndex = -1
                            }else selectedIndex = index
                        },
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(text = card.id.toString(), autoSize = TextAutoSize.StepBased())
                }
            }
        }

        ParallaxItem(
            modifier = Modifier.fillMaxSize(),
            screenIndex = cardList.size
        ) {
            Text(text = it.scrollDownOffset.toString(), color = White)
        }
    }
}

@Preview
@Composable
private fun CardStackPreview() {
    MaterialTheme {
        CardStack(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}