package com.naulian.parallax

//Linear Mapping
fun lmap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedLmap(x: Int, xMin: Int, xMax: Int, yMin: Float, yMax: Float): Float {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return lmap(x, xMin, xMax, yMin, yMax)
}

fun lmap(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    return yMin + (yMax - yMin) * (x - xMin) / (xMax - xMin)
}

fun closedLmap(x: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): Int {
    if (x > xMax) return yMax
    if (x < xMin) return yMin
    return lmap(x, xMin, xMax, yMin, yMax)
}