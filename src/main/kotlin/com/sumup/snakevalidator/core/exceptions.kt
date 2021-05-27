package com.sumup.snakevalidator.core

class FruitNotFoundException(message: String = "Fruit not found.") : RuntimeException(message)

class InvalidMoveException(message: String = "Invalid move.") : RuntimeException(message)

class InvalidDirectionException(message: String = "Invalid direction.") : RuntimeException(message)