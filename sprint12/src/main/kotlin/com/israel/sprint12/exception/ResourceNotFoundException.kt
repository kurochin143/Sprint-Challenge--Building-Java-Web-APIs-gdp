package com.israel.sprint12.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND)
open class ResourceNotFoundException : RuntimeException {

    companion object {
        const val serialVersionUid = 1L
    }

    constructor()

    constructor(msg: String) : super(msg)

    constructor(msg: String, cause: Throwable) : super(msg, cause)
}