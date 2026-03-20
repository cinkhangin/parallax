package com.example.parallax

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.annotation.RememberInComposition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Interpolatable.Companion.lerp
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.ckgin.modify.Bold
import com.ckgin.modify.Fonts
import com.ckgin.modify.HugeIcons
import com.ckgin.modify.themeStyles
import com.ckgin.parallax.Parallax
import com.ckgin.parallax.ParallaxItem
import com.ckgin.parallax.VectorImage
import com.ckgin.parallax.half
import com.ckgin.parallax.scale
import com.ckgin.parallax.slideX
import com.ckgin.parallax.slideY
import com.ckgin.parallax.yOffSet

@Composable
fun Showcase() {
    Parallax(
        screenCount = 4,
        modifier = Modifier.fillMaxSize()
    ) {
        // Background layer that stays throughout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFD600))
        )

        // Screen 0: Intro
        ParallaxItem(screenIndex = 0, contentAlignment = Alignment.BottomCenter) { progress ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .slideX(progress.exitProgress, to = 1000),
                        text = "Scrollytelling".uppercase(),
                        style = themeStyles.displaySmall.Bold,
                        textAlign = TextAlign.Center,
                        fontFamily = Fonts.JetBrainsMono,
                        color = Color.DarkGray
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .slideX(progress.exitProgress, to = -1000),
                        text = "ShowCase".uppercase(),
                        style = themeStyles.displayLarge.Bold.copy(letterSpacing = 4.sp),
                        textAlign = TextAlign.Center,
                        fontFamily = Fonts.JetBrainsMono,
                        color = Color.Gray
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .slideY(progress.exitProgress, to = -2000),
                        text = """
                            Scrollytelling is a digital storytelling technique where narrative elements—such as text, images, and data visualizations—are triggered or controlled by the user's scrolling progress. Unlike standard "scroll-reveal" effects that simply animate a component into view, scrollytelling often "locks" the background in place (using sticky positioning) while content transforms or evolves as the user continues to scroll. This creates an immersive, cinematic experience that allows the reader to pace the story themselves, effectively turning a webpage into an interactive presentation.
                        """.trimIndent(),
                        style = themeStyles.bodyLarge,
                        textAlign = TextAlign.Start,
                        fontFamily = Fonts.JetBrainsMono,
                        color = Color.Gray
                    )
                }


                //Scrollup indicator
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .slideY(progress.exitProgress, to = 1000)
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = CircleShape
                        )
                        .padding(6.dp)
                ) {

                    val density = LocalDensity.current

                    val destination = remember {
                        with(density) {
                            // 200dp is the container
                            // 12dp is padding 6dp * 2
                            // 52dp is the size of the container - padding
                            -(200.dp - 12.dp - 52.dp).toPx().toInt()
                        }
                    }

                    val offsetX = remember { Animatable(0) }
                    val alphaAnim = remember { Animatable(0f) }

                    var targetOffset by remember { mutableIntStateOf(0) }
                    var targetAlpha by remember { mutableFloatStateOf(0f) }

                    LaunchedEffect(targetOffset) {
                        if (targetOffset != 0) {
                            alphaAnim.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = 500)
                            )
                            offsetX.animateTo(
                                targetValue = destination,
                                animationSpec = tween(durationMillis = 1000)
                            )
                            targetOffset = 0
                        } else {
                            alphaAnim.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 500)
                            )
                            offsetX.snapTo(0)
                            targetOffset = destination
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .offset {
                                yOffSet(offsetX.value)
                            }
                            .scale(alphaAnim.value)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .rotate(90f)
                        )
                    }
                }

            }
        }

        // Screen 1: Elements Flying In
        ParallaxItem(screenIndex = 1) { progress ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .slideY(progress.enterProgress, height, 0)
                    .slideY(progress.exitProgress, 0, -height)
                    .background(Color(0xFF000C5B))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(lerp(20.dp, 0.dp, progress.exitProgress)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val screenDp = LocalWindowInfo.current.containerDpSize
                    // Floating shapes
                    Box(
                        modifier = Modifier
                            .width(lerp(200.dp, screenDp.width, progress.exitProgress))
                            .height(lerp(200.dp, screenDp.height, progress.exitProgress))
                            .align(Alignment.Center)
                            .slideX(progress.enterProgress, -height, 0)
                            .clip(RoundedCornerShape(lerp(100.dp, 0.dp, progress.exitProgress)))
                            .background(Color(0xFFDD2C00))
                    )

                    Text(
                        text = "DYNAMIC",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(progress.enterProgress * (1f - progress.exitProgress))
                            .scale(progress.enterProgress),
                        style = themeStyles.displayLarge.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = """
                        In scrollytelling, dynamic refers to the fluid, real-time relationship between a user’s scroll position and the transformation of on-screen elements. Unlike static pages where content simply moves up or down, dynamic scrollytelling treats the scrollbar as a "playback head" or scrub bar for an animation timeline. This allows properties like scale, opacity, rotation, and data point positions to shift incrementally as the user scrolls, creating a highly interactive experience where the narrative responds directly to the user's physical input.
                    """.trimIndent(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .slideY(progress.enterProgress, from = 2000),
                    style = themeStyles.bodyLarge,
                    textAlign = TextAlign.Start,
                    fontFamily = Fonts.JetBrainsMono,
                    color = Color.Gray
                )
            }
        }

        // Screen 2: Layered Images
        ParallaxItem(screenIndex = 2) { progress ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "DEPTH",
                    modifier = Modifier
                        .slideY(progress.enterProgress, from = -1500),
                    style = themeStyles.displayLarge.Bold,
                    color = Color.White
                )

                Text(
                    text = """
                        In the context of design and scrollytelling, depth is the perceived distance between the user and the various layers of an interface. It is achieved by manipulating the spatial relationship of elements—using techniques like shadows, blur, and scale—to break the "flatness" of the screen. By layering content along a Z-axis, you create a visual hierarchy that guides the user's eye, making the most important elements appear "closer" and more interactive.
                    """.trimIndent(),
                    modifier = Modifier
                        .scale(progress.enterProgress),
                    style = themeStyles.bodyLarge.Bold,
                    color = Color.White
                )
            }
        }

        // Screen 3: Final Call
        ParallaxItem(screenIndex = 3) { progress ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        lerp(
                            Color.Transparent,
                            Color(0xFF6200EE),
                            progress.enterProgress
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "EXPLORE",
                        style = themeStyles.displayLarge.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .rotate(progress.enterProgress * 360f)
                            .scale(progress.enterProgress)

                    )
                    Text(
                        text = "The possibilities",
                        style = themeStyles.headlineMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .alpha(progress.enterProgress)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShowcasePreview() {
    Showcase()
}

@RememberInComposition
fun Animatable(
    initialValue: Int,
    visibilityThreshold: Int = 0,
): Animatable<Int, AnimationVector1D> =
    Animatable(initialValue, Int.VectorConverter, visibilityThreshold)
