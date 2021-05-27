package com.sumup.snakevalidator.api

import com.sumup.snakevalidator.SnakeValidatorApplication
import com.sumup.snakevalidator.common.ErrorResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus

@SpringBootTest(
    classes = [SnakeValidatorApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
internal class ValidatorRestControllerIntegrationTest @Autowired constructor(private val apiIntegrationTest: APIIntegrationTest) {

    @Test
    internal fun `should return 400 when trying to create a new game if width and height are negative`() {
        val width = -1
        val height = -1

        val actual = apiIntegrationTest.newGame<ErrorResponse>(width, height)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    internal fun `should return 400 when trying to create a new game if width and height are 0`() {
        val width = 0
        val height = 0

        val actual = apiIntegrationTest.newGame<ErrorResponse>(width, height)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    internal fun `should create a new game only if both width and height are positive`() {
        val width = 5
        val height = 5

        val actual = apiIntegrationTest.newGame<StateDto>(width, height)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)

        with(actual.body!!) {
            assertThat(this.width).isEqualTo(width)
            assertThat(this.height).isEqualTo(height)
            assertThat(this.score).isEqualTo(0)
            assertThat(this.snake).isEqualTo(SnakeDto(x = 0, y = 0, velX = 1, velY = 0))
        }
    }

    @Test
    internal fun `should not allow diagonal move`() {
        val request = StateValidationRequest(
            gameId = "1",
            width = 5,
            height = 5,
            score = 1,
            fruit = FruitDto(x = 3, y = 3),
            snake = SnakeDto(x = 0, y = 0, velX = 1, velY = 0),
            ticks = listOf(TickDto(velX = 1, velY = 1))
        )

        val actual = apiIntegrationTest.validate<ErrorResponse>(request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.I_AM_A_TEAPOT)
    }

    @Test
    internal fun `should not allow U turn`() {
        val request = StateValidationRequest(
            gameId = "1",
            width = 5,
            height = 5,
            score = 1,
            fruit = FruitDto(x = 3, y = 3),
            snake = SnakeDto(x = 0, y = 0, velX = 1, velY = 0),
            ticks = listOf(TickDto(velX = 1, velY = 0), TickDto(velX = -1, velY = 0))
        )

        val actual = apiIntegrationTest.validate<ErrorResponse>(request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.I_AM_A_TEAPOT)
    }

    @Test
    internal fun `should not allow the snake out of the board`() {
        val request = StateValidationRequest(
            gameId = "1",
            width = 2,
            height = 2,
            score = 1,
            fruit = FruitDto(x = 1, y = 1),
            snake = SnakeDto(x = 0, y = 0, velX = 1, velY = 0),
            ticks = listOf(TickDto(velX = 1, velY = 0), TickDto(velX = 1, velY = 0), TickDto(velX = 1, velY = 0))
        )

        val actual = apiIntegrationTest.validate<ErrorResponse>(request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.I_AM_A_TEAPOT)
    }

    @Test
    internal fun `should return 404 when the fruit is not found`() {
        val request = StateValidationRequest(
            gameId = "1",
            width = 2,
            height = 2,
            score = 1,
            fruit = FruitDto(x = 1, y = 1),
            snake = SnakeDto(x = 0, y = 0, velX = 1, velY = 0),
            ticks = listOf(TickDto(velX = 1, velY = 0))
        )

        val actual = apiIntegrationTest.validate<ErrorResponse>(request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    internal fun `should return 200 when the fruit is found`() {
        val request = StateValidationRequest(
            gameId = "1",
            width = 5,
            height = 5,
            score = 1,
            fruit = FruitDto(x = 1, y = 1),
            snake = SnakeDto(x = 0, y = 0, velX = 1, velY = 0),
            ticks = listOf(TickDto(velX = 1, velY = 0), TickDto(velX = 0, velY = 1))
        )

        val actual = apiIntegrationTest.validate<StateDto>(request)

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)

        with(actual.body!!) {
            assertThat(this.width).isEqualTo(5)
            assertThat(this.height).isEqualTo(5)
            assertThat(this.score).isEqualTo(2)
            assertThat(this.snake).isEqualTo(SnakeDto(x = 1, y = 1, velX = 0, velY = 1))
        }
    }
}