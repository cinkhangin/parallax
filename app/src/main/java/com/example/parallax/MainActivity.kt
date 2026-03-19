package com.example.parallax

import android.R.attr.fontWeight
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ckgin.modify.Fonts
import com.ckgin.parallax.Parallax
import com.ckgin.parallax.ParallaxItem
import com.ckgin.parallax.VectorImage
import com.ckgin.parallax.closedMap
import com.ckgin.parallax.half
import com.ckgin.parallax.hideSystemBars
import com.ckgin.parallax.scale
import com.ckgin.parallax.slideY
import com.ckgin.parallax.yOffSet
import com.ckgin.parallax.yOffsetFrom
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            hideSystemBars()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ParallaxExample()
            }
        }
    }
}


@Composable
fun ParallaxExample(modifier: Modifier = Modifier) {
    Parallax(
        screenCount = 2,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
            .clip(CircleShape)
    ) {
        ParallaxItem(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDC2A0)),
            screenIndex = 0,
            contentAlignment = Alignment.BottomCenter
        ) { progress ->
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, height.half),
                drawable = R.drawable.bg_level10
            )

            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(20))),
                drawable = R.drawable.bg_level9
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(30))),
                drawable = R.drawable.bg_level8
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(40))),
                drawable = R.drawable.bg_level7
            )

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 120.dp)
                    .offset {
                        yOffSet(
                            y = closedMap(
                                progress.exitProgress,
                                0.4f,
                                1f,
                                0f,
                                height.scale(80).toFloat()
                            ).toInt()
                        )
                    },
                text = "PARALLAX",
                fontFamily = Fonts.JetBrainsMono,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                color = Color.White
            )

            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(50))),
                drawable = R.drawable.bg_level6
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(60))),
                drawable = R.drawable.bg_level5
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(70))),
                drawable = R.drawable.bg_level4
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(80))),
                drawable = R.drawable.bg_level3
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -(height.scale(90))),
                drawable = R.drawable.bg_level2
            )
            VectorImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .slideY(progress.exitProgress, 0, -height),
                drawable = R.drawable.bg_level1
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .slideY(progress.exitProgress, height, 0)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(progress.exitProgress)
                    .background(Color.Black)
            )
        }

        ParallaxItem(screenIndex = 1) { progress ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { yOffsetFrom(progress.enterProgress, height, 0) }
                    .background(Color.Black)
            )
        }
    }
}
