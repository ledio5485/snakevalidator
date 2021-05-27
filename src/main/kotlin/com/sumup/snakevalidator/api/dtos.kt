package com.sumup.snakevalidator.api

import org.hibernate.validator.constraints.Range
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class StateDto(
    val gameId: String,
    val width: Int,
    val height: Int,
    val score: Int,
    val fruit: FruitDto,
    val snake: SnakeDto
)

data class FruitDto(
    @field: Min(0)
    val x: Int,
    @field: Min(0)
    val y: Int
)

data class SnakeDto(
    @field: Min(0)
    val x: Int,
    @field: Min(0)
    val y: Int,
    @field:Range(min = -1L, max = 1L)
    val velX: Int,
    @field:Range(min = -1L, max = 1L)
    val velY: Int
)

data class StateValidationRequest(
    @field: NotBlank
    val gameId: String,
    @field: Positive
    val width: Int,
    @field: Positive
    val height: Int,
    @field: Min(0)
    val score: Int,
    @field:Valid
    val fruit: FruitDto,
    @field:Valid
    val snake: SnakeDto,
    val ticks: List<TickDto>
)

data class TickDto(
    @field:Range(min = -1L, max = 1L)
    val velX: Int,
    @field:Range(min = -1L, max = 1L)
    val velY: Int
)
