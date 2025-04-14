package com.naulian.parallax

fun Int.checkIfZero() {
    if (this == 0) print("is Zero")
    else print("is not Zero")
}

fun main() {
    0.checkIfZero()
}