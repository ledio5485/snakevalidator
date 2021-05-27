package com.sumup.snakevalidator

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [SnakeValidatorApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SnakeValidatorApplicationTests {

    @Test
    fun contextLoads() {
    }

}
