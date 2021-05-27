package com.sumup.snakevalidator.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
internal class APIIntegrationTest @Autowired constructor(private val restTemplate: TestRestTemplate) {

    final inline fun <reified T> newGame(width: Int, height: Int): ResponseEntity<T> =
        restTemplate.getForEntity("${ValidatorResource.NEW_URI}?w=$width&h=$height", T::class.java)

    final inline fun <reified T> validate(request: StateValidationRequest): ResponseEntity<T> =
        restTemplate.postForEntity(ValidatorResource.VALIDATE_URI, request, T::class.java)
}