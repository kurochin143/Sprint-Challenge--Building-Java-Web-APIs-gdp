package com.israel.sprint12

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.DispatcherServlet

@SpringBootApplication
class Sprint12Application {

    companion object {
        val countryList = CountryList()
    }
}

fun main(args: Array<String>) {
    val ctx = runApplication<Sprint12Application>(*args)

    val dispatcherServlet = ctx.getBean("dispatcherServlet") as DispatcherServlet
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true)
}
