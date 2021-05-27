package com.sumup.snakevalidator.core

import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

data class Game(
    val gameId: String = UUID.randomUUID().toString(),
    val board: Rectangle,
    val score: AtomicInteger = AtomicInteger(),
    val snake: Snake = Snake(),
    var fruit: Point = randomPointNotIn(board, snake.position)
) {
    companion object {
        fun new(width: Int, height: Int) = Game(board = Rectangle(Dimension(width, height)))
    }

    init {
        check(score.get() >= 0)
        check(board.contains(snake.position))
        check(board.contains(fruit))
    }

    fun applyTicks(ticks: Array<Vector2D>) {
        checkNoDiagonalMove(*ticks)

        var isFruitFound = false
        ticks.forEach { tick ->
            snake.applyTick(tick)

            if (board.contains(snake.position).not()) throw InvalidMoveException("The snake went out of the board.")

            if (fruitFound()) {
                isFruitFound = true
                incrementScore()
                generateNewFruit()
            }
        }

        if (!isFruitFound) throw FruitNotFoundException()
    }

    private fun fruitFound() = snake.position == fruit

    private fun incrementScore() {
        score.getAndIncrement()
    }

    private fun generateNewFruit() {
        fruit = randomPointNotIn(board, snake.position)
    }
}
