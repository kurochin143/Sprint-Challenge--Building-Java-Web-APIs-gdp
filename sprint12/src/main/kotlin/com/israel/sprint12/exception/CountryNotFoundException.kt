package com.israel.sprint12.exception

import java.lang.RuntimeException

class CountryNotFoundException : ResourceNotFoundException {

    constructor(id: Long) : super("Could not find Country by id: $id")
}
