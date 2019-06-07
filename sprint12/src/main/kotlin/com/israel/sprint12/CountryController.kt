package com.israel.sprint12

import com.israel.sprint12.exception.CountryNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class CountryController {

    companion object: Log()

    // /names
    @GetMapping("/names")
    fun namesAsc(): ResponseEntity<*> {
        logger.info("/names accessed")

        val outCountryList = mutableListOf<Country>()

        outCountryList.addAll(Sprint12Application.countryList.data)

        outCountryList.sortBy { it.name }

        return ResponseEntity(outCountryList, HttpStatus.OK)
    }

    // /economy
    @GetMapping("/economy")
    fun economyDes(): ResponseEntity<*> {
        logger.info("/economy accessed")

        val outCountryList = mutableListOf<Country>()

        outCountryList.addAll(Sprint12Application.countryList.data)

        outCountryList.sortByDescending { it.gdp }

        return ResponseEntity(outCountryList, HttpStatus.OK)
    }

    // /country/{id}
    @GetMapping("/country/{id}")
    fun singleCountry(@PathVariable("id") id: Long): ResponseEntity<*> {
        logger.info("/country/{id} accessed")

        var outCountry: Country? = null

        Sprint12Application.countryList.data.forEach {
            if (it.id == id) {
                outCountry = it
            }
        }

        outCountry ?: throw CountryNotFoundException(id)

        return ResponseEntity(outCountry, HttpStatus.OK)
    }

    // /country/stats/median
    @GetMapping("/country/stats/median")
    fun getCountryWithMediaGDP(): ResponseEntity<*> {
        logger.info("/country/stats/median accessed")

        var countryListByGDP = mutableListOf<Country>()
        countryListByGDP.addAll(Sprint12Application.countryList.data)

        countryListByGDP.sortBy { it.gdp }

        val outCountry = countryListByGDP[(countryListByGDP.size / 2) + 1]

        return ResponseEntity(outCountry, HttpStatus.OK)
    }

    // /economy/table
    @GetMapping("/economy/table")
    fun displayCountryTableByEconomy(): ModelAndView {
        logger.info("/economy/table accessed")

        val mav = ModelAndView()
        mav.viewName = "economyTable"
        val countryListByEconomy = mutableListOf<Country>()
        countryListByEconomy.addAll(Sprint12Application.countryList.data)

        countryListByEconomy.sortByDescending { it.gdp }

        mav.addObject("countryList", countryListByEconomy)

        return mav
    }

}