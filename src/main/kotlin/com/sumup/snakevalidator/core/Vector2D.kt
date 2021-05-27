package com.sumup.snakevalidator.core

import kotlin.math.atan2

data class Vector2D(val x: Double, val y: Double) {
    fun angle(vector: Vector2D) = 180 / Math.PI * atan2(x * vector.y - y * vector.x, x * vector.x + y * vector.y)
}