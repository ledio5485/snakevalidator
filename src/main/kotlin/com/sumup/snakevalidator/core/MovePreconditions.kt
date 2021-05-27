package com.sumup.snakevalidator.core

fun checkNoDiagonalMove(vararg directions: Vector2D) {
    val refVector = Vector2D(x = 1.0, y = 0.0)
    if (directions.any { it.angle(refVector) % 90 != 0.0 }) {
        throw InvalidMoveException("A diagonal move is not allowed.")
    }
}

fun checkMaxRotation(vector1: Vector2D, vector2: Vector2D, maxRotation: Double = 90.0) {
    if (vector1.angle(vector2) > maxRotation) {
        throw InvalidMoveException("A rotation bigger than $maxRotation° is not allowed.")
    }
}