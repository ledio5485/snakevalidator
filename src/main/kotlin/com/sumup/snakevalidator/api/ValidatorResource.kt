package com.sumup.snakevalidator.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.Valid
import javax.validation.constraints.Positive

@RequestMapping
interface ValidatorResource {

    companion object {
        const val NEW_URI = "/new"
        const val VALIDATE_URI = "/validate"
    }

    @GetMapping(NEW_URI)
    fun newGame(
        @RequestParam("w") @Positive width: Int,
        @RequestParam("h") @Positive height: Int
    ): ResponseEntity<StateDto>

    @PostMapping(VALIDATE_URI)
    fun validate(@RequestBody @Valid request: StateValidationRequest): ResponseEntity<StateDto>
}