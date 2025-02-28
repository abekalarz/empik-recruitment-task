package com.example.empik_recruitment_task.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: MutableList<String> = ArrayList()
        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return handleExceptionInternal(
            ex, apiError, headers, apiError.status, request
        )
    }
}

data class ApiError(
    var status: HttpStatusCode,
    var message: String? = null,
    var errors: List<String>? = null
)
