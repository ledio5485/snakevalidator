package com.sumup.snakevalidator.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.awt.Point

internal class SnakeTest {

    @Test
    internal fun `should create a new Snake`() {
        val actual = Snake()

        val expected = Snake(position = Point(), velocity = Vector2D(x = 1.0, y = 0.0))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `should apply a tick moving forward`() {
        val actual = Snake()

        actual.applyTick(Vector2D(x = 1.0, y = 0.0))

        val expected = Snake(position = Point(1, 0), Vector2D(x = 1.0, y = 0.0))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `should apply a tick rotating left`() {
        val actual = Snake()

        actual.applyTick(Vector2D(x = 0.0, y = 1.0))

        val expected = Snake(position = Point(0, 1), velocity = Vector2D(x = 0.0, y = 1.0))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `should apply a tick rotating right`() {
        val actual = Snake()

        actual.applyTick(Vector2D(x = 0.0, y = -1.0))

        val expected = Snake(position = Point(0, -1), Vector2D(x = 0.0, y = -1.0))
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    internal fun `should not allow U turn`() {
        val actual = Snake()

        assertThrows<InvalidMoveException> {
            actual.applyTick(Vector2D(x = -1.0, y = 0.0))
        }
    }
}