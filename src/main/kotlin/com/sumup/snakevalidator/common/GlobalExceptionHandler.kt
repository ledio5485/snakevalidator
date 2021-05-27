package com.sumup.snakevalidator.common

import com.sumup.snakevalidator.common.ErrorResponseEntity.Companion.badRequest
import com.sumup.snakevalidator.common.ErrorResponseEntity.Companion.notFound
import com.sumup.snakevalidator.common.ErrorResponseEntity.Companion.serverError
import com.sumup.snakevalidator.common.ErrorResponseEntity.Companion.teapot
import com.sumup.snakevalidator.core.FruitNotFoundException
import com.sumup.snakevalidator.core.InvalidDirectionException
import com.sumup.snakevalidator.core.InvalidMoveException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleBadRequestException(ex: Exception, request: WebRequest): ErrorResponseEntity {
        return badRequest(ex.message)
    }

    @ExceptionHandler(value = [FruitNotFoundException::class])
    fun handleNotFoundException(ex: Exception, request: WebRequest): ErrorResponseEntity {
        return notFound(ex.message)
    }

    @ExceptionHandler(value = [InvalidMoveException::class, InvalidDirectionException::class])
    fun handleTeapotException(ex: Exception, request: WebRequest): ErrorResponseEntity {
        return teapot(ex.message)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleGenericException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return serverError(ex.message)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map { objectError ->
            if (objectError is FieldError) {
                "'${objectError.field}:${objectError.rejectedValue}' ${objectError.defaultMessage}"
            } else {
                objectError.defaultMessage
            }
        }
        return handleExceptionInternal(ex, ErrorResponse(status, errors.joinToString()), headers, status, request)
    }
}