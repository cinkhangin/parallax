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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.naulian.glow_compose.font
import com.naulian.modify.bold
import com.naulian.modify.themeStyles
import com.naulian.parallax.ParallaxColumn
import com.naulian.parallax.ParallaxLayout
import com.naulian.parallax.calculatedHeightPx
import com.naulian.parallax.closedLmap
import com.naulian.parallax.half
import com.naulian.parallax.lmap
import com.naulian.parallax.parallax
import com.naulian.parallax.rangeTo
import com.naulian.parallax.rememberCalculation
import com.naulian.parallax.rememberParallaxColumnState
import com.naulian.parallax.rememberScrollOffset
import com.naulian.parallax.scale
import com.naulian.parallax.smartLmap
import com.naulian.parallax.xOffSet
import com.naulian.parallax.yOffSet
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
            val view = LocalView.current
            WindowCompat.getInsetsController(window, view).apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }

            ParallaxLayout {
                val offSet by rememberScrollOffset(state, height)
                //Slide 0
                //==================================================================================

                val slide0Text by rememberCalculation {
                    closedLmap(offSet, 1000, height, 0, height.scale(80))
                }

                val slide0level10 by rememberCalculation {
                    smartLmap(offSet, 0, 0 rangeTo height.half)
                    //lmap(offSet, 0, height, 0, height / 2)
                }

                val slide0level9 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.2f).toInt())
                }
                val slide0level8 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.3f).toInt())
                }

                val slide0level7 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.4f).toInt())
                }
                val slide0level6 by remember {
                    derivedStateOf {
                        lmap(offSet, 0, height, 0, -(height * 0.5f).toInt())
                    }
                }
                val slide0level5 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.6f).toInt())
                }
                val slide0level4 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.7f).toInt())
                }
                val slide0level3 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.8f).toInt())
                }
                val slide0level2 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 0.9f).toInt())
                }

                val slide0level1 by rememberCalculation {
                    lmap(offSet, 0, height, 0, -(height * 1f).toInt())
                }

                val slide0Alpha by rememberCalculation {
                    closedLmap(offSet, 0, height, 0f, 1f)
                }

                //Slide 1
                //==================================================================================
                val slide1Text by rememberCalculation {
                    lmap(offSet, 1000, height, -2400, 0)
                }

                val slide1Alpha by rememberCalculation {
                    closedLmap(offSet, height * 2, height * 3, 1f, 0f)
                }

                val slide1XLeft by rememberCalculation {
                    lmap(offSet, height * 2, height * 3, 0, -1200)
                }

                val slide1XRight by rememberCalculation {
                    lmap(offSet, height * 2, height * 3, 0, 1200)
                }

                //Slide 2
                //==================================================================================
                val slide2Alpha by rememberCalculation {
                    if (offSet <= height * 3) closedLmap(offSet, height * 2, height * 3, 0f, 1f)
                    else closedLmap(offSet, height * 3, height * 4, 1f, 0f)
                }

                val slide2YBack by rememberCalculation {
                    smartLmap(
                        offset = offSet,
                        index = 3,
                        outRange = 0 rangeTo  1600,
                        exitOutRange = -1600 rangeTo 0
                    )
                   /* if (offSet <= height * 3) lmap(offSet, height * 2, height * 3, -1600, 0)
                    else lmap(offSet, height * 3, height * 4, 0, 1600)*/
                }

                val slide2Scale by rememberCalculation {
                    if (offSet <= height * 3)
                        closedLmap(offSet, height * 2, height * 3, 0f, 1f)
                    else closedLmap(offSet, height * 3, height * 4, 1f, 0f)
                }

                // Slide 3
                //==================================================================================
                val slide3Alpha by rememberCalculation {
                    if (offSet <= height * 4)
                        closedLmap(offSet, height * 3, height * 4, 0f, 1f)
                    else closedLmap(offSet, height * 4, height * 5, 1f, 0f)
                }

                val slide3Rotate by rememberCalculation {
                    closedLmap(offSet, height * 3, height * 4, 180, 0) % 360f
                }

                val slide3YText by rememberCalculation {
                    if (offSet <= height * 4)
                        lmap(offSet, height * 3, height * 4, -1600, 0)
                    else lmap(offSet, height * 4, height * 5, 0, 1600)
                }

                // Slide 4
                //==================================================================================
                val slide4Alpha by rememberCalculation {
                    if (offSet <= height * 5)
                        closedLmap(offSet, height * 4, height * 5, 0f, 1f)
                    else closedLmap(offSet, height * 5, height * 6, 1f, 0f)
                }

                val slide4YBack by rememberCalculation {
                    if (offSet <= height * 5)
                        lmap(offSet, height * 4, height * 5, -1600, 0)
                    else lmap(offSet, height * 5, height * 6, 0, 1600)
                }

                val slide4XFront by rememberCalculation {
                    lmap(offSet, height * 4, height * 6, 2000, -2000)
                }

                // Slide 5
                //==================================================================================
                val slide5Alpha by rememberCalculation {
                    if (offSet <= height * 6)
                        closedLmap(offSet, height * 5, height * 6, 0f, 1f)
                    else closedLmap(offSet, height * 6, height * 7, 1f, 0f)
                }

                val slide5YBack by rememberCalculation {
                    if (offSet <= height * 6)
                        lmap(offSet, height * 5, height * 6, -1600, 0)
                    else lmap(offSet, height * 6, height * 7, 0, 1600)
                }

                val slide5YFront by rememberCalculation {
                    lmap(offSet, height * 5, height * 6, -1000, 0)
                }

                //==================================================================================

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFEDC2A0)),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level10) },
                        drawable = R.drawable.bg_level10
                    )

                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level9) },
                        drawable = R.drawable.bg_level9
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level8) },
                        drawable = R.drawable.bg_level8
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level7) },
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
                            .offset { yOffSet(slide0level6) },
                        drawable = R.drawable.bg_level6
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level5) },
                        drawable = R.drawable.bg_level5
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level4) },
                        drawable = R.drawable.bg_level4
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level3) },
                        drawable = R.drawable.bg_level3
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level2) },
                        drawable = R.drawable.bg_level2
                    )
                    VectorImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset { yOffSet(slide0level1) },
                        drawable = R.drawable.bg_level1
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(slide0Alpha)
                            .background(Color.Black)
                    )
                }

                ParallaxColumn {
                    parallax(background = Color.Transparent)
                    parallax(background = Color.Black) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 120.dp)
                                .offset { yOffSet(slide1Text) },
                            text = "PARALLAX",
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 64.sp,
                            color = Color.White
                        )

                        TextContent(
                            modifier = Modifier
                                .alpha(slide0Alpha),
                            content = """
                                Parallax is the apparent shift in the position of an object when
                                observed from different viewpoints. This effect occurs because an
                                object's position appears to change relative to its background depending
                                on the observer's angle or position. Parallax is often used in astronomy
                                to measure the distance of stars from Earth by observing their movement
                                against more distant celestial bodies. On a smaller scale, parallax is
                                also noticeable in everyday life when looking at objects from different
                                angles, making it a crucial concept in fields like photography,
                                navigation, and virtual reality.
                            """.trimIndent(),
                            textColor = Color.White
                        )
                    }
                    parallax(background = Color.White)
                    parallax(background = Color.Black) {
                        TextContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(slide2Alpha)
                                .offset { yOffSet(slide2YBack) },
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
                    }
                    parallax(
                        background = Color.White,
                        contentAlignment = Alignment.TopCenter
                    ) {
                        TextContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(slide3Alpha)
                                .offset { yOffSet(y = slide3YText) },
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

                        Image(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 40.dp)
                                .size(240.dp)
                                .rotate(slide3Rotate),
                            painter = painterResource(R.drawable.ic_nike),
                            contentDescription = null
                        )
                    }
                    parallax(background = Color(0xFFF40009)) {
                        TextContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(slide4Alpha)
                                .offset { yOffSet(slide4YBack) },
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
                    }
                    parallax(background = Color.White) {
                        TextContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(slide5Alpha)
                                .offset { yOffSet(slide5YBack) },
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
                    }
                }

                //Slide 1
                //==================================================================================

                //Slide 1
                //==================================================================================
                TextContent(
                    modifier = Modifier
                        .alpha(slide1Alpha)
                        .offset { xOffSet(slide1XRight) },
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
                        .offset { xOffSet(slide1XLeft) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Exploring Iconic Brands: Legacy, Innovation, and Influence",
                        style = themeStyles.displayMedium.bold(),
                        fontFamily = font,
                        overflow = TextOverflow.Ellipsis,
                    )
                }


                // Slide 2
                //==================================================================================

                LogoContent(
                    logo = R.drawable.ic_apple,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(slide2Scale),
                )

                // Slide 3
                //==================================================================================


                // Slide 4
                //==================================================================================

                LogoContent(
                    logo = R.drawable.ic_cocacola,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset { xOffSet(slide4XFront) },
                )

                // Slide 5
                //==================================================================================
                LogoContent(
                    logo = R.drawable.ic_tesla,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset { yOffSet(slide5YFront) },
                )
            }
        }
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
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
            Text(
                modifier = Modifier
                    .padding(24.dp),
                text = content.asParagraph,
                style = themeStyles.bodyLarge,
                fontFamily = font,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
        }
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
fun VectorImage(modifier: Modifier = Modifier, @DrawableRes drawable: Int) {
    Image(
        modifier = modifier
            .scale(1.32f),
        painter = painterResource(drawable),
        contentScale = ContentScale.FillWidth,
        contentDescription = null
    )
}


