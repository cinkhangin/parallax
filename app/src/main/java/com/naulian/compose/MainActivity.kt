package com.naulian.compose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.snapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naulian.glow_compose.font
import com.naulian.modify.bold
import com.naulian.modify.themeStyles
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlin.math.roundToInt

@HiltAndroidApp
class App : Application()

const val itemHeightPx = 2400 // change this to your device height in pixels or get it programmatically from Configuration
const val densityAdjustment = 0.001f
val itemHeightDp
    @Composable get() : Float {
        return with(LocalDensity.current) {
            println(density)
            itemHeightPx / (density - densityAdjustment)
        }
    }

fun mapInt2Float(value: Int, b1: Int, b2: Int, c1: Float, c2: Float): Float {
    if(value > b2) return c2
    return c1 + (c2 - c1) * (value - b1) / (b2 - b1)
}

fun mapInt2Int(value: Int, b1: Int, b2: Int, c1: Int, c2: Int): Int {
    if(value > b2) return c2
    return c1 + (c2 - c1) * (value - b1) / (b2 - b1)
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            MaterialTheme {
                val state = rememberLazyListState()

                val scrollIndex by remember {
                    derivedStateOf {
                        state.firstVisibleItemIndex
                    }
                }

                val scrollOffset by remember {
                    derivedStateOf {
                        state.firstVisibleItemScrollOffset
                    }
                }

                val columnOffset by remember {
                    derivedStateOf {
                        (scrollIndex * itemHeightPx) + scrollOffset
                    }
                }

                //FirstSlide
                //==================================================================================
                val slide1Alpha by remember {
                    derivedStateOf {
                        val delta = mapInt2Float(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 1f,
                            c2 = 0f
                        )
                        delta
                    }
                }

                val slide1XLeft by remember {
                    derivedStateOf {
                        val delta = mapInt2Int(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 0,
                            c2 = -1000
                        )

                        delta
                    }
                }

                val slide1XRight by remember {
                    derivedStateOf {
                        val delta = mapInt2Int(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 0,
                            c2 = 1000
                        )

                        delta
                    }
                }

                //Second Slide
                //==================================================================================

                val slide2Alpha by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx) mapInt2Float(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 0f,
                            c2 = 1f
                        ) else mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx,
                            b2 = itemHeightPx * 2,
                            c1 = 1f,
                            c2 = 0f
                        )

                        delta
                    }
                }

                val slide2YBack by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx) mapInt2Int(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 400,
                            c2 = 0
                        ) else mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx,
                            b2 = itemHeightPx * 2,
                            c1 = 0,
                            c2 = -400
                        )

                        delta
                    }
                }

                val slide2Scale by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx) mapInt2Float(
                            value = columnOffset,
                            b1 = 0,
                            b2 = itemHeightPx,
                            c1 = 0f,
                            c2 = 1f
                        ) else mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx,
                            b2 = itemHeightPx * 2,
                            c1 = 1f,
                            c2 = 0f
                        )

                        delta
                    }
                }

                // Third Slide
                //==================================================================================

                val slide3Alpha by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 2) mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx,
                            b2 = itemHeightPx * 2,
                            c1 = 0f,
                            c2 = 1f
                        ) else mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx * 2,
                            b2 = itemHeightPx * 3,
                            c1 = 1f,
                            c2 = 0f
                        )

                        delta
                    }
                }

                val slide3YBack by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 2) mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx,
                            b2 = itemHeightPx * 2,
                            c1 = 400,
                            c2 = 0
                        ) else mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 2,
                            b2 = itemHeightPx * 3,
                            c1 = 0,
                            c2 = -400
                        )

                        delta
                    }
                }

                // Slide 4
                //==================================================================================

                val slide4Alpha by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 3) mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx * 2,
                            b2 = itemHeightPx * 3,
                            c1 = 0f,
                            c2 = 1f
                        ) else mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx * 3,
                            b2 = itemHeightPx * 4,
                            c1 = 1f,
                            c2 = 0f
                        )

                        delta
                    }
                }

                val slide4YBack by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 3) mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 2,
                            b2 = itemHeightPx * 3,
                            c1 = 400,
                            c2 = 0
                        ) else mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 3,
                            b2 = itemHeightPx * 4,
                            c1 = 0,
                            c2 = -400
                        )

                        delta
                    }
                }


                val slide4XFront by remember {
                    derivedStateOf {
                        val delta = mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 2,
                            b2 = itemHeightPx * 4,
                            c1 = 2000,
                            c2 = -2000
                        )

                        delta
                    }
                }

                // Slide 4
                //==================================================================================

                val slide5Alpha by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 4) mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx * 3,
                            b2 = itemHeightPx * 4,
                            c1 = 0f,
                            c2 = 1f
                        ) else mapInt2Float(
                            value = columnOffset,
                            b1 = itemHeightPx * 4,
                            b2 = itemHeightPx * 5,
                            c1 = 1f,
                            c2 = 0f
                        )

                        delta
                    }
                }

                val slide5YBack by remember {
                    derivedStateOf {
                        val delta = if (columnOffset <= itemHeightPx * 4) mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 3,
                            b2 = itemHeightPx * 4,
                            c1 = 400,
                            c2 = 0
                        ) else mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 4,
                            b2 = itemHeightPx * 5,
                            c1 = 0,
                            c2 = -400
                        )

                        delta
                    }
                }


                val slide5YFront by remember {
                    derivedStateOf {
                        val delta = mapInt2Int(
                            value = columnOffset,
                            b1 = itemHeightPx * 3,
                            b2 = itemHeightPx * 4,
                            c1 = -1000,
                            c2 = 0
                        )

                        delta
                    }
                }

                //==================================================================================

                val snappingLayout =
                    remember(state) { SnapLayoutInfoProvider(state, SnapPosition.Center) }
                val snapFlingBehavior = rememberMySnapFlingBehavior(snappingLayout)

                LazyColumn(
                    state = state,
                    flingBehavior = snapFlingBehavior
                ) {
                    slideContainer(background = Color.White)
                    slideContainer(background = Color.Black)
                    slideContainer(background = Color.White, contentAlignment = Alignment.TopCenter) {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 40.dp)
                                .size(240.dp),
                            painter = painterResource(R.drawable.ic_nike),
                            contentDescription = null
                        )
                    }
                    slideContainer(background = Color(0xFFF40009))
                    slideContainer(background = Color.White)
                }

                //Slide 1
                //==================================================================================

                Column(
                    modifier = Modifier.alpha(slide1Alpha)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 24.dp)
                            .offset {
                                Offset(x = slide1XLeft.toFloat(), y = 0f).toIntOffset()
                            },
                        text = "Exploring Iconic Brands: Legacy, Innovation, and Influence",
                        style = themeStyles.displayMedium.bold(),
                        fontFamily = font
                    )

                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .offset {
                                Offset(x = slide1XRight.toFloat(), y = 0f).toIntOffset()
                            },
                        text = """
                                    In a world saturated with products and services, certain brands stand out not just for their quality, but for their ability to connect with consumers on a deeper level. These brands have successfully created identities that resonate with their audience, influencing not just purchasing decisions but also lifestyles. Today, we will explore four iconic brands that have made a significant impact in their respective industries: Apple, Nike, Coca-Cola, and Tesla. We will delve into their histories, brand strategies, and the ways they have shaped consumer culture.
                                """.trimIndent(),
                        fontFamily = font
                    )
                }

                // Slide 2
                //==================================================================================

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide2Alpha)
                        .offset {
                            Offset(x = 0f, y = slide2YBack.toFloat()).toIntOffset()
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 40.dp),
                        text = """
                        Apple Inc., founded in 1976 by Steve Jobs, Steve Wozniak, and Ronald Wayne, is a global technology company known for its innovative products and design-centric approach. With the launch of the Macintosh in 1984 and the iPhone in 2007, Apple revolutionized personal computing and mobile technology. The brand's emphasis on user experience, sleek aesthetics, and cutting-edge features has created a loyal customer base and a strong global presence. Apple’s ecosystem, including products like the iPad, Apple Watch, and services such as the App Store and iCloud, continues to shape the technology landscape, positioning the company as a leader in innovation and consumer electronics.
                    """.trimIndent(),
                        style = themeStyles.bodyLarge,
                        fontFamily = font,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                }


                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        modifier = Modifier
                            .padding(64.dp)
                            .size(200.dp)
                            .scale(slide2Scale),
                        painter = painterResource(R.drawable.ic_apple),
                        contentDescription = null
                    )
                }

                // Slide 3
                //==================================================================================
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide3Alpha)
                        .offset {
                            Offset(x = 0f, y = slide3YBack.toFloat()).toIntOffset()
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 40.dp),
                        text = """
                        Nike, founded in 1964 by Bill Bowerman and Phil Knight as Blue Ribbon Sports, has grown into a global leader in athletic footwear, apparel, and equipment. Renamed Nike in 1971, after the Greek goddess of victory, the brand is known for its "Just Do It" slogan and iconic Swoosh logo. Nike's success is built on its innovative products, cutting-edge technology in sportswear, and strong athlete endorsements, including partnerships with legendary athletes like Michael Jordan and Serena Williams. Beyond products, Nike has transformed the sports industry by promoting empowerment, self-expression, and a culture of fitness, making it a cultural and athletic icon worldwide.
                        """.trimIndent(),
                        style = themeStyles.bodyLarge,
                        fontFamily = font,
                    )
                }

                // Slide 4
                //==================================================================================

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide4Alpha)
                        .offset {
                            Offset(x = 0f, y = slide4YBack.toFloat()).toIntOffset()
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 40.dp),
                        text = """
                        Coca-Cola, created in 1886 by pharmacist Dr. John Stith Pemberton, is one of the world’s most iconic and recognizable beverage brands. Originally marketed as a medicinal tonic, Coca-Cola quickly became a popular soft drink, thanks to its unique flavor and innovative marketing strategies. Over the decades, the brand has solidified its global presence through memorable advertising campaigns, such as the "Share a Coke" initiative and its long-standing association with happiness and refreshment. With its distinctive red-and-white logo and classic glass bottle, Coca-Cola has transcended the beverage industry, becoming a symbol of American culture and a leader in global marketing.
                        """.trimIndent(),
                        style = themeStyles.bodyLarge,
                        fontFamily = font,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 64.dp)
                        .offset {
                            Offset(x = slide4XFront.toFloat(), y = 0f).toIntOffset()
                        },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(240.dp)
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.ic_cocacola),
                        contentDescription = null
                    )
                }

                // Slide 5
                //==================================================================================

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide5Alpha)
                        .offset {
                            Offset(x = 0f, y = slide5YBack.toFloat()).toIntOffset()
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 40.dp),
                        text = """
                        Tesla, founded in 2003 by engineers Martin Eberhard and Marc Tarpenning, with Elon Musk joining shortly after, is a trailblazer in the electric vehicle (EV) industry and renewable energy solutions. Known for its mission to accelerate the world's transition to sustainable energy, Tesla has revolutionized the automotive sector with cutting-edge electric cars like the Model S, Model 3, and Model X, offering high performance, long range, and sleek design. Tesla's innovative technologies, such as its Autopilot self-driving system and battery advancements, have set new standards for the industry. Beyond vehicles, Tesla also focuses on energy storage and solar products, making it a key player in the shift toward sustainability.
                        """.trimIndent(),
                        style = themeStyles.bodyLarge,
                        fontFamily = font,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 64.dp)
                        .offset {
                            Offset(x = 0f, y = slide5YFront.toFloat()).toIntOffset()
                        },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(240.dp)
                            .fillMaxWidth(),
                        painter = painterResource(R.drawable.ic_tesla),
                        contentDescription = null
                    )
                }
            }
        }
    }
}


fun LazyListScope.slideContainer(
    modifier: Modifier = Modifier,
    background: Color,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    item {
        Box(
            modifier = modifier
                .fillMaxSize()
                .height(itemHeightDp.dp)
                .background(background),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}

@Composable
fun rememberMySnapFlingBehavior(
    snapLayoutInfoProvider: SnapLayoutInfoProvider
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


