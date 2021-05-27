package com.sumup.snakevalidator.core

import java.awt.Point
import java.awt.Rectangle
import kotlin.random.Random

fun randomPointNotIn(board: Rectangle, notIn: Point): Point {
    var point = randomPoint(board)
    while (point == notIn) {
        point = randomPointNotIn(board, notIn)
    }
    return point
}

private fun randomPoint(board: Rectangle) = with(board) {
    Point(Random.nextInt(x, x + width), Random.nextInt(y, y + height))
}