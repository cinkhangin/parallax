# Parallax

A lightweight Android library for creating beautiful parallax effects in your Jetpack Compose applications.

## Installation

Add the dependency to your app's build.gradle file:

```gradle
dependencies {
    implementation 'com.yourusername:parallax:0.1.0-alpha01'
}
```

## Features

- Create multi-screen parallax layouts with smooth transitions
- Control animation timing and direction
- Slide elements with precise control over their movement
- Support for various content types including images, text, and custom composables
- Edge-to-edge display support

## Usage

### Basic Implementation

```kotlin
Parallax(screenCount = 2) {
    // First screen
    ParallaxItem(
        modifier = Modifier.fillMaxSize(),
        screenIndex = 0
    ) { progress ->
        // Your content here with animations based on progress
    }
    
    // Second screen
    ParallaxItem(
        screenIndex = 1
    ) { progress ->
        // Your second screen content
    }
}
```

### Complete Example

Here's how to create a rich parallax effect with multiple layers:

```kotlin
Parallax(screenCount = 2) {
    ParallaxItem(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDC2A0)),
        screenIndex = 0,
        contentAlignment = Alignment.BottomCenter
    ) { progress ->
        // Background layers that move at different speeds
        VectorImage(
            modifier = Modifier
                .fillMaxWidth()
                .slideY(progress.exitProgress, 0, height.half),
            drawable = R.drawable.bg_layer1
        )

        VectorImage(
            modifier = Modifier
                .fillMaxWidth()
                .slideY(progress.exitProgress, 0, -(height.scale(20))),
            drawable = R.drawable.bg_layer2
        )
        
        // Text that moves with the parallax effect
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
            fontSize = 64.sp,
            color = Color.White
        )

        // Additional background layers
        // ...
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
```

## Key Components

### Parallax

The main container that manages the parallax effect across multiple screens.

Parameters:
- `screenCount`: Number of screens in the parallax sequence
- `content`: Content lambda where you define your screens

### ParallaxItem

Represents a single screen in the parallax sequence.

Parameters:
- `modifier`: Modifier for styling the item
- `screenIndex`: Position in the sequence (0-based)
- `contentAlignment`: How content should be aligned within the item
- `content`: Content lambda that receives a progress object

### Progress Object

Each ParallaxItem's content lambda receives a progress object with:
- `enterProgress`: Progress value as the screen enters (0 to 1)
- `exitProgress`: Progress value as the screen exits (0 to 1)

## Helper Extensions

### slideY

```kotlin
Modifier.slideY(progress, startY, endY)
```
Slides an element along the Y-axis based on progress value.

### scale

```kotlin
Int.scale(scalar: Float): Int
```
Scales an integer value by a float scalar.

### half

```kotlin
val Int.half: Int
```
Extension property that returns half of the integer value.
