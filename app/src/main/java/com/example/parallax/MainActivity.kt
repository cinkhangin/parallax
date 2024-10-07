package com.example.parallax

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.naulian.composer.ui.ComposerUI
import com.naulian.glow_compose.font
import com.naulian.parallax.OffsetCalculationSpec
import com.naulian.parallax.ParallaxColumn
import com.naulian.parallax.ParallaxLayout
import com.naulian.parallax.calculateYOffset
import com.naulian.parallax.closedLmap
import com.naulian.parallax.half
import com.naulian.parallax.into
import com.naulian.parallax.lmap
import com.naulian.parallax.parallax
import com.naulian.parallax.rememberCalculation
import com.naulian.parallax.rememberScrollOffset
import com.naulian.parallax.scale
import com.naulian.parallax.smartClosedLmap
import com.naulian.parallax.smartLmap
import com.naulian.parallax.xOffSet
import com.naulian.parallax.yOffSet
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlin.math.roundToInt

@HiltAndroidApp
class App : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val view = LocalView.current
            WindowCompat.getInsetsController(window, view).apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            ParallaxLayout {
                val offset by rememberScrollOffset(state, height)
                //Slide 0
                //==================================================================================

                val slide0Text by rememberCalculation {
                    closedLmap(offset, 1000, height, 0, height.scale(80))
                }

                //sun
                val slide0level10 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, height.half)
                }

                val slide0level9 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(20))
                }
                val slide0level8 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(30))
                }

                val slide0level7 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(40))
                }
                val slide0level6 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(50))
                }
                val slide0level5 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(60))
                }
                val slide0level4 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(70))
                }
                val slide0level3 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(80))
                }
                val slide0level2 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height.scale(90))
                }

                val slide0level1 by calculateYOffset {
                    OffsetCalculationSpec(offset, 0, 0, -height)
                }

                val doorAlpha by rememberCalculation {
                    closedLmap(offset, 0, height, 0f, 1f)
                }

                val slide1Alpha by rememberCalculation {
                    closedLmap(offset, height - 10, height, 1f, 0f)
                }

                val welcomeAlpha by rememberCalculation {
                    smartClosedLmap(offset, 0, 0f into 1f, 1f into 0f)
                }

                val alphaExample by rememberCalculation {
                    smartClosedLmap(offset, 6, 1f into 0f)
                }

                val rotateExample by rememberCalculation {
                    smartClosedLmap(offset, 7, 0 into 1000) % 360f
                }

                val scaleXExample by rememberCalculation {
                    smartClosedLmap(offset, 8, 1f into 2f)
                }
                val scaleYExample by rememberCalculation {
                    smartClosedLmap(offset, 8, 1f into 3f)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEDC2A0)),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level10 },
                        drawable = R.drawable.bg_level10
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level9 },
                        drawable = R.drawable.bg_level9
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level8 },
                        drawable = R.drawable.bg_level8
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level7 },
                        drawable = R.drawable.bg_level7
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 120.dp)
                            .offset { yOffSet(slide0Text) },
                        text = "PARALLAX",
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 64.sp,
                        color = Color.White
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level6 },
                        drawable = R.drawable.bg_level6
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level5 },
                        drawable = R.drawable.bg_level5
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level4 },
                        drawable = R.drawable.bg_level4
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level3 },
                        drawable = R.drawable.bg_level3
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level2 },
                        drawable = R.drawable.bg_level2
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { slide0level1 },
                        drawable = R.drawable.bg_level1
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset {
                            val y = smartClosedLmap(offset, 9, height + 500 into 0)
                            yOffSet(y)
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level9
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level8
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.001f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level7
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.01f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level6
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.1f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level5
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.3f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level4
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.5f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level3
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 120.dp),
                        text = "THANK YOU",
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 64.sp,
                        color = Color.White
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.7f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level2
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height.scale(0.9f))
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level1
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset {
                                val y = smartClosedLmap(offset, 10, 0 into -height)
                                yOffSet(y)
                            },
                        contentScale = ContentScale.FillHeight,
                        drawable = R.drawable.lc_level0
                    )
                }

                ParallaxColumn {
                    parallax()
                    parallax(background = Color.White) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(slide1Alpha)
                                .background(Color.Black)
                        )
                    }
                    parallax(background = Color.White) {
                        ComposerUI(
                            modifier = Modifier.padding(16.dp),
                            source = """
                                #3 How to create a Parallax Effect
                                *x you need a Scrollable UI
                                {
                                .kt
                                LazyColumn {}
                                }
                                *x you need to know the scroll position
                                {
                                .kt
                                val state = rememberLazyListState()
                                val position by remember {
                                    derivedStateOf {
                                        val itemIndex = state.firstVisibleItemIndex
                                        val itemOffset = state.firstVisibleItemScrollOffset
                                        (itemIndex * itemHeight) + itemOffset
                                    }
                                }
                                }
                            """.trimIndent()
                        )
                    }

                    parallax(background = Color.White, contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Red)
                        )

                        Box(modifier = Modifier
                            .size(200.dp)
                            .offset {
                                val y = smartLmap(offset, 3, 500 into 0, 0 into 2000)
                                yOffSet(y)
                            }
                            .background(Color.Green)
                        )

                        Box(modifier = Modifier
                            .size(300.dp)
                            .offset {
                                val y = smartLmap(offset, 3, 1000 into 0, 0 into 3000)
                                yOffSet(y)
                            }
                            .background(Color.Blue)
                        )
                    }
                    parallax(background = Color.White) {
                        val a = SliderState(value = 0f, steps = 100, valueRange = 0f..1f)
                        val b by rememberCalculation {
                            (a.value * 100).roundToInt()
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Slider(modifier = Modifier.padding(24.dp), state = a)

                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val x = lmap(b, 0, 100, 0, 500)
                                    xOffSet(x)
                                }
                                .background(Color.Green)
                            )

                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val x = lmap(b, 0, 100, 0, 300)
                                    xOffSet(x)
                                }
                                .background(Color.Blue)
                            )

                            ComposerUI(
                                modifier = Modifier.padding(16.dp),
                                source = """
                                    {
                                    .kt
                                    /*
                                           (yMax - yMin) * (x - xMin)
                                    yMin + --------------------------
                                                   xMax - xMin
                                    */
                                    //Linear Mapping
                                    fun linearMap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
                                        return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
                                    }
                                    }
                            
                                """.trimIndent()
                            )
                        }
                    }

                    parallax(background = Color.White) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ComposerUI(
                                source = """
                                #3 Offset
                            """.trimIndent()
                            )

                            ComposerUI(
                                modifier = Modifier.padding(16.dp),
                                source = """
                          
                                    {
                                    .kt
                                    //Example
                                    Box(modifier = Modifier
                                        .size(50.dp)
                                        .offset {
                                            val x = lmap(offset, 0, 1000, 0, 300)
                                            IntOffset(x = x, y = 0)
                                        }
                                        .background(Color.Blue)
                            )
                                    }
                            
                                """.trimIndent()
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val x = smartClosedLmap(offset, 5, 0 into 500)
                                    xOffSet(x)
                                }
                                .background(Color.Yellow)
                            )

                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val y = smartClosedLmap(offset, 5, 0 into 500)
                                    yOffSet(y)
                                }
                                .background(Color.Green)
                            )
                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val x = smartClosedLmap(offset, 5, 0 into -500)
                                    xOffSet(x)
                                }
                                .background(Color.Red)
                            )
                            Box(modifier = Modifier
                                .size(50.dp)
                                .offset {
                                    val y = smartClosedLmap(offset, 5, 0 into -500)
                                    yOffSet(y)
                                }
                                .background(Color.Blue)
                            )
                        }
                    }

                    parallax(background = Color.White) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ComposerUI(
                                source = """
                                #3 Alpha
                            """.trimIndent()
                            )

                            ComposerUI(
                                modifier = Modifier.padding(16.dp),
                                source = """
                                    {
                                    .kt
                                    //Example
                                    val alpha = lmap(offset, 0, 1000, 0f, 1f)
                                    Box(modifier = Modifier
                                        .size(50.dp)
                                        .alpha(alpha)
                                        .background(Color.Blue)
                                    )
                                    }
                                """.trimIndent()
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .size(50.dp)
                                    .alpha(alphaExample)
                                    .background(Color.Blue)
                            )
                        }
                    }

                    parallax(background = Color.White) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ComposerUI(
                                source = """
                                #3 Rotate
                            """.trimIndent()
                            )

                            ComposerUI(
                                modifier = Modifier.padding(16.dp),
                                source = """
                                    {
                                    .kt
                                    //Example
                                    val degree = lmap(offset, 0, 1000, 0, 360)
                                    Box(modifier = Modifier
                                        .size(50.dp)
                                        .rotate(degree)
                                        .background(Color.Blue)
                                    )
                                    }
                                """.trimIndent()
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .rotate(rotateExample)
                                    .background(Color.Blue)
                            )
                        }
                    }

                    parallax(background = Color.White) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ComposerUI(
                                source = """
                                #3 Scale
                            """.trimIndent()
                            )

                            ComposerUI(
                                modifier = Modifier.padding(16.dp),
                                source = """
                                    {
                                    .kt
                                    //Example
                                    val x = lmap(offset, 0, 1000, 1f, 2f)
                                    val y = lmap(offset, 0, 1000, 1f, 3f)
                                    Box(modifier = Modifier
                                        .size(50.dp)
                                        .scale(scaleX = x, scaleY = y)
                                        .background(Color.Blue)
                                    )
                                    }
                                """.trimIndent()
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .scale(scaleX = scaleXExample, scaleY = scaleYExample)
                                    .background(Color.Blue)
                            )
                        }
                    }

                    parallax(background = Color.White, contentAlignment = Alignment.Center) {
                        ComposerUI(
                            modifier = Modifier.padding(16.dp),
                            source = """
                                "The only Limit is your imagination"
                                
                                #1 Q \& A
                            """.trimIndent()
                        )
                    }

                    parallax()
                    parallax(background = Color(0xFF1F1515))
                }


                //door
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .offset {
                            val x = smartClosedLmap(offset, 1, 0 into -1000)
                            xOffSet(x)
                        }
                        .alpha(doorAlpha)
                        .background(Color.Black)
                    )
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .offset {
                            val x = smartClosedLmap(offset, 1, 0 into 1000)
                            xOffSet(x)
                        }
                        .alpha(doorAlpha)
                        .background(Color.Black)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.alpha(welcomeAlpha),
                        text = "Welcome",
                        color = Color.White,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

@Composable
fun VectorImage(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    Image(
        modifier = modifier
            .scale(1.32f),
        painter = painterResource(drawable),
        contentScale = contentScale,
        contentDescription = null
    )
}


