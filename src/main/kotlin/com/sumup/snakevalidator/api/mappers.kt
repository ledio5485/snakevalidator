package com.sumup.snakevalidator.api

import com.sumup.snakevalidator.core.Game
import com.sumup.snakevalidator.core.Snake
import com.sumup.snakevalidator.core.Vector2D
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.util.concurrent.atomic.AtomicInteger

fun StateValidationRequest.validate() =
    Game(
        gameId = this.gameId,
        board = Rectangle(Dimension(width, height)),
        score = AtomicInteger(this.score),
        snake = Snake(
            position = Point(this.snake.x, this.snake.y),
            velocity = Vector2D(x = this.snake.velX.toDouble(), y = this.snake.velY.toDouble())
        ),
        fruit = Point(this.fruit.x, this.fruit.y)
    ).also { game ->
        game.applyTicks(ticks.map { dto -> dto.toTick() }.toTypedArray())
    }

fun TickDto.toTick() = Vector2D(x = this.velX.toDouble(), y = this.velY.toDouble())

fun Game.getStateDto() =
    StateDto(
        gameId = this.gameId,
        width = this.board.width,
        height = this.board.height,
        score = this.score.get(),
        fruit = this.fruit.toFruitDto(),
        snake = this.snake.toSnakeDto()
    )

private fun Point.toFruitDto() = FruitDto(x = this.x, y = this.y)

private fun Snake.toSnakeDto() =
    SnakeDto(x = position.x, y = position.y, velX = velocity.x.toInt(), velY = velocity.y.toInt())

