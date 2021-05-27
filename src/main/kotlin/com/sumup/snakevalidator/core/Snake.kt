package com.sumup.snakevalidator.core

import java.awt.Point

data class Snake(val position: Point = Point(), var velocity: Vector2D = Vector2D(x = 1.0, y = 0.0)) {

    fun applyTick(tick: Vector2D) {
        translate(tick)
        rotate(tick)
    }

    private fun translate(tick: Vector2D) = this.position.translate(tick.x.toInt(), tick.y.toInt())

    private fun rotate(tick: Vector2D) {
        checkMaxRotation(velocity, tick)

        this.velocity = tick
    }
}