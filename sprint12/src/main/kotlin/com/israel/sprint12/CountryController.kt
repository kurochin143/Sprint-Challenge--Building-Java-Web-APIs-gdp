package com.israel.sprint12

import com.israel.sprint12.exception.CountryNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class CountryController {

    // /names
    @GetMapping("/names")
    fun namesAsc(): ResponseEntity<*> {
        val outCountryList = mutableListOf<Country>()

        outCountryList.addAll(Sprint12Application.gdpList.data)

        outCountryList.sortBy { it.name }

        return ResponseEntity(outCountryList, HttpStatus.OK)
    }

    // /economy
    @GetMapping("/economy")
    fun economyDes(): ResponseEntity<*> {
        val outCountryList = mutableListOf<Country>()

        outCountryList.addAll(Sprint12Application.gdpList.data)

        outCountryList.sortByDescending { it.gdp }

        return ResponseEntity(outCountryList, HttpStatus.OK)
    }

    // /country/{id}
    @GetMapping("/country/{id}")
    fun singleCountry(@PathVariable("id") id: Long) {
        var outCountry: Country? = null

        Sprint12Application.gdpList.data.forEach {
            if (it.id == id) {
                outCountry = it
            }
        }

        outCountry ?: throw CountryNotFoundException(id)
    }

}