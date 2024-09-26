package com.naulian.compose

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.naulian.glow_compose.font
import com.naulian.modify.bold
import com.naulian.modify.themeStyles
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@HiltAndroidApp
class App : Application()

val MainActivity.deviceHeightPx
    @Composable get(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return windowManager.currentWindowMetrics.bounds.height()
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

val MainActivity.calculatedPx
    @Composable get() : Int {
        val px = with(LocalDensity.current) {
            calculatedDp.value * density
        }
        return ceil(px).toInt()
    }
val MainActivity.calculatedDp
    @Composable get() : Dp {
        val dp = with(LocalDensity.current) {
            deviceHeightPx / density
        }
        return floor(dp).dp
    }

fun map(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    if (x > xMax) return yMax
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun map(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    if (x > xMax) return yMax
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

            MaterialTheme {
                CheckDensity()
                val height = calculatedPx
                val state = rememberLazyListState()

                val offSet by remember {
                    derivedStateOf {
                        (state.firstVisibleItemIndex * height) + state.firstVisibleItemScrollOffset
                    }
                }

                //FirstSlide
                //==================================================================================
                val slide1Alpha by remember {
                    derivedStateOf {
                        map(offSet, 0, height, 1f, 0f)
                    }
                }

                val slide1XLeft by remember {
                    derivedStateOf {
                        map(offSet, 0, height, 0, -1000)
                    }
                }

                val slide1XRight by remember {
                    derivedStateOf {
                        map(offSet, 0, height, 0, 1000)
                    }
                }

                //Second Slide
                //==================================================================================

                val slide2Alpha by remember {
                    derivedStateOf {
                        if (offSet <= height) map(offSet, 0, height, 0f, 1f)
                        else map(offSet, height, height * 2, 1f, 0f)
                    }
                }

                val slide2YBack by remember {
                    derivedStateOf {
                        if (offSet <= height) map(offSet, 0, height, 400, 0)
                        else map(offSet, height, height * 2, 0, -400)
                    }
                }

                val slide2Scale by remember {
                    derivedStateOf {
                        if (offSet <= height)
                            map(offSet, 0, height, 0f, 1f)
                        else map(offSet, height, height * 2, 1f, 0f)
                    }
                }

                // Third Slide
                //==================================================================================

                val slide3Alpha by remember {
                    derivedStateOf {
                        if (offSet <= height * 2)
                            map(offSet, height, height * 2, 0f, 1f)
                        else map(offSet, height * 2, height * 3, 1f, 0f)
                    }
                }

                val slide3YBack by remember {
                    derivedStateOf {
                        if (offSet <= height * 2)
                            map(offSet, height, height * 2, 400, 0)
                        else map(offSet, height * 2, height * 3, 0, -400)
                    }
                }

                // Slide 4
                //==================================================================================

                val slide4Alpha by remember {
                    derivedStateOf {
                        if (offSet <= height * 3)
                            map(offSet, height * 2, height * 3, 0f, 1f)
                        else map(offSet, height * 3, height * 4, 1f, 0f)
                    }
                }

                val slide4YBack by remember {
                    derivedStateOf {
                        if (offSet <= height * 3)
                            map(offSet, height * 2, height * 3, 400, 0)
                        else map(offSet, height * 3, height * 4, 0, -400)
                    }
                }


                val slide4XFront by remember {
                    derivedStateOf {
                        map(offSet, height * 2, height * 4, 2000, -2000)
                    }
                }

                // Slide 4
                //==================================================================================

                val slide5Alpha by remember {
                    derivedStateOf {
                        if (offSet <= height * 4)
                            map(offSet, height * 3, height * 4, 0f, 1f)
                        else map(offSet, height * 4, height * 5, 1f, 0f)
                    }
                }

                val slide5YBack by remember {
                    derivedStateOf {
                        if (offSet <= height * 4)
                            map(offSet, xMin = height * 3, xMax = height * 4, yMin = 400, yMax = 0)
                        else map(offSet, height * 4, height * 5, 0, -400)
                    }
                }


                val slide5YFront by remember {
                    derivedStateOf {
                        map(offSet, height * 3, height * 4, -1000, 0)
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
                    slideContainer(
                        background = Color.White,
                        contentAlignment = Alignment.TopCenter
                    ) {
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

                TextContent(
                    modifier = Modifier
                        .alpha(slide1Alpha)
                        .offset {
                            Offset(x = slide1XRight.toFloat(), y = 0f).toIntOffset()
                        },
                    content = """
                            In a world saturated with products and services, certain brands stand
                            out not just for their quality, but for their ability to connect with
                            consumers on a deeper level. These brands have successfully created
                            identities that resonate with their audience, influencing not just
                            purchasing decisions but also lifestyles. Today, we will explore four
                            iconic brands that have made a significant impact in their respective
                            industries: Apple, Nike, Coca-Cola, and Tesla. We will delve into their
                            histories, brand strategies, and the ways they have shaped consumer
                            culture.
                        """.trimIndent(),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(24.dp)
                        .offset {
                            Offset(x = slide1XLeft.toFloat(), y = 0f).toIntOffset()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Exploring Iconic Brands: Legacy, Innovation, and Influence",
                        style = themeStyles.displayMedium.bold(),
                        fontFamily = font,
                        overflow = TextOverflow.Ellipsis
                    )
                }


                // Slide 2
                //==================================================================================
                TextContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide2Alpha)
                        .offset {
                            Offset(x = 0f, y = slide2YBack.toFloat()).toIntOffset()
                        },
                    content = """
                        Apple Inc., founded in 1976 by Steve Jobs, Steve Wozniak, and Ronald Wayne,
                        is a global technology company known for its innovative products and
                        design-centric approach. With the launch of the Macintosh in 1984 and the
                        iPhone in 2007, Apple revolutionized personal computing and mobile
                        technology. The brand's emphasis on user experience, sleek aesthetics, and
                        cutting-edge features has created a loyal customer base and a strong global
                        presence. Apple’s ecosystem, including products like the iPad, Apple Watch,
                        and services such as the App Store and iCloud, continues to shape the
                        technology landscape, positioning the company as a leader in innovation and
                        consumer electronics.
                    """.trimIndent(),
                    textColor = Color.White
                )

                LogoContent(
                    logo = R.drawable.ic_apple,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(slide2Scale),
                )

                // Slide 3
                //==================================================================================
                TextContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide3Alpha)
                        .offset {
                            Offset(x = 0f, y = slide3YBack.toFloat()).toIntOffset()
                        },
                    content = """
                        Nike, founded in 1964 by Bill Bowerman and Phil Knight as Blue Ribbon
                        Sports, has grown into a global leader in athletic footwear, apparel, and
                        equipment. Renamed Nike in 1971, after the Greek goddess of victory, the
                        brand is known for its "Just Do It" slogan and iconic Swoosh logo. Nike's
                        success is built on its innovative products, cutting-edge technology in
                        sportswear, and strong athlete endorsements, including partnerships with
                        legendary athletes like Michael Jordan and Serena Williams. Beyond products,
                        Nike has transformed the sports industry by promoting empowerment,
                        self-expression, and a culture of fitness, making it a cultural and athletic
                        icon worldwide.
                    """.trimIndent()
                )

                // Slide 4
                //==================================================================================

                TextContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide4Alpha)
                        .offset {
                            Offset(x = 0f, y = slide4YBack.toFloat()).toIntOffset()
                        },
                    content = """
                        Coca-Cola, created in 1886 by pharmacist Dr. John Stith Pemberton, is one
                        of the world’s most iconic and recognizable beverage brands. Originally
                        marketed as a medicinal tonic, Coca-Cola quickly became a popular soft
                        drink, thanks to its unique flavor and innovative marketing strategies. Over
                        the decades, the brand has solidified its global presence through memorable
                        advertising campaigns, such as the "Share a Coke" initiative and its
                        long-standing association with happiness and refreshment. With its
                        distinctive red-and-white logo and classic glass bottle, Coca-Cola has
                        transcended the beverage industry, becoming a symbol of American culture and
                        a leader in global marketing.
                    """.trimIndent(),
                    textColor = Color.White
                )

                LogoContent(
                    logo = R.drawable.ic_cocacola,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset {
                            Offset(x = slide4XFront.toFloat(), y = 0f).toIntOffset()
                        },
                )

                // Slide 5
                //==================================================================================

                TextContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(slide5Alpha)
                        .offset {
                            Offset(x = 0f, y = slide5YBack.toFloat()).toIntOffset()
                        },
                    content = """
                        Tesla, founded in 2003 by engineers Martin Eberhard and Marc Tarpenning,
                        with Elon Musk joining shortly after, is a trailblazer in the electric
                        vehicle (EV) industry and renewable energy solutions. Known for its mission
                        to accelerate the world's transition to sustainable energy, Tesla has
                        revolutionized the automotive sector with cutting-edge electric cars like
                        the Model S, Model 3, and Model X, offering high performance, long range,
                        and sleek design. Tesla's innovative technologies, such as its Autopilot
                        self-driving system and battery advancements, have set new standards for
                        the industry. Beyond vehicles, Tesla also focuses on energy storage and
                        solar products, making it a key player in the shift toward sustainability.
                    """.trimIndent()
                )

                LogoContent(
                    logo = R.drawable.ic_tesla,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset {
                            Offset(x = 0f, y = slide5YFront.toFloat()).toIntOffset()
                        },
                )
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
        val context = (LocalContext.current as MainActivity)
        Box(
            modifier = modifier
                .fillMaxSize()
                .height(context.calculatedDp)
                .background(background),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}

@Composable
fun TextContent(
    modifier: Modifier = Modifier,
    content: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(modifier = Modifier.height(320.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            text = content.asParagraph,
            style = themeStyles.bodyLarge,
            fontFamily = font,
            color = textColor,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LogoContent(modifier: Modifier = Modifier, @DrawableRes logo: Int) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(320.dp)
                .padding(64.dp),
            painter = painterResource(logo),
            contentDescription = null
        )
    }
}

val String.asParagraph get() = trimIndent().replace("\n", " ")

@Composable
fun rememberMySnapFlingBehavior(
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

@Composable
fun MainActivity.CheckDensity() {
    val screenDensity = LocalDensity.current
    println("Density: ${screenDensity.density}")
    println("deviceHeightDp: $calculatedDp")
    println("deviceHeightPx: $calculatedPx")
}


