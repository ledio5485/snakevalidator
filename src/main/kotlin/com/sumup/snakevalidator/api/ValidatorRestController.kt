package com.sumup.snakevalidator.api

import com.sumup.snakevalidator.core.Game
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class ValidatorRestController : ValidatorResource {

    override fun newGame(width: Int, height: Int): ResponseEntity<StateDto> {
        logger.info { "new game for width $width and height $height" }

        val newGame = Game.new(width = width, height = height)

        return ResponseEntity.ok(newGame.getStateDto())
            .also { logger.info { "New game created: ${it.body}" } }
    }

    override fun validate(request: StateValidationRequest): ResponseEntity<StateDto> {
        logger.info { "validation request: $request" }

        val existingGame = request.validate()

        return ResponseEntity.ok(existingGame.getStateDto())
            .also { logger.info { "New state of the game ${it.body} after applying ${request.ticks}" } }
    }
}