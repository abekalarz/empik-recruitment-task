package com.example.empik_recruitment_task.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: List<String> = exception.bindingResult.allErrors.map { error ->
            when (error) {
                is FieldError -> "${error.field}: ${error.defaultMessage}"
                else -> "${error.objectName}: ${error.defaultMessage}"
            }
        }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, exception.localizedMessage, errors)
        return handleExceptionInternal(
            exception, apiError, headers, apiError.status, request
        )
    }
}

data class ApiError(
    val status: HttpStatusCode,
    val message: String? = null,
    val errors: List<String>
)
