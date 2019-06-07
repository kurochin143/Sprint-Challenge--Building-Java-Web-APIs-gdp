package com.israel.sprint12

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Sprint12Application {

    companion object {
        val gdpList = CountryList()
    }
}

fun main(args: Array<String>) {
    runApplication<Sprint12Application>(*args)
}
