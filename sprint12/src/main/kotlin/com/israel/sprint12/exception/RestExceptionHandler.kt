package com.israel.sprint12.exception

import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler {

    @Autowired
    private var messageSource: MessageSource? = null

    constructor()

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(rnfe: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<*> {
        val errorDetails = ErrorDetails()
        errorDetails.setTimestamp(Date().time)
        errorDetails.status = HttpStatus.NOT_FOUND.value()
        errorDetails.title = "Resource Not Found"
        errorDetails.detail = rnfe.message
        errorDetails.developerMessage = rnfe.javaClass.name

        return ResponseEntity(errorDetails, null, HttpStatus.NOT_FOUND)
    }

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetails = ErrorDetails()
        errorDetails.setTimestamp(Date().time)
        errorDetails.status = HttpStatus.BAD_REQUEST.value()
        errorDetails.title = ex.propertyName
        errorDetails.detail = ex.message
        errorDetails.developerMessage = ex.javaClass.name

        return ResponseEntity(errorDetails, null, HttpStatus.NOT_FOUND)
    }

    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {

        val errorDetails = ErrorDetails()
        errorDetails.setTimestamp(Date().time)
        errorDetails.status = HttpStatus.BAD_REQUEST.value()
        errorDetails.title = ex.requestURL
        errorDetails.detail = request.getDescription(true)
        errorDetails.developerMessage = "Rest Handler Not Found (check for valid URI)"

        return ResponseEntity(errorDetails, null, HttpStatus.NOT_FOUND)
    }

}