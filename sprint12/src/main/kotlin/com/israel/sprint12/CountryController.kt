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

    @GetMapping("/")
    fun home(): ModelAndView {
        val mav = ModelAndView()
        mav.viewName = "home"
        val endpoints = mutableListOf<String>()
        endpoints.add("/names")
        endpoints.add("/economy")
        endpoints.add("/country/{id}")
        endpoints.add("/country/stats/median")
        endpoints.add("/total")
        endpoints.add("/names/{start letter}/{end letter}")
        endpoints.add("/gdp/list/{start gdp}/{end gdp}")

        mav.addObject("endpoints", endpoints)

        return mav
    }

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

    // /total
    @GetMapping("/total")
    fun getTotalGDP(): ResponseEntity<*> {
        logger.info("/total accessed")

        var totalGDP: Long = 0
        Sprint12Application.countryList.data.forEach {
            totalGDP += it.gdp
        }

        val outCountry = Country("Total", totalGDP.toString())

        return ResponseEntity(outCountry, HttpStatus.OK)
    }

    // /names/{start letter}/{end letter}
    // inclusive
    @GetMapping("/names/{start letter}/{end letter}")
    fun displayCountriesStartEndName(
            @PathVariable("start letter") startLetter: Char,
            @PathVariable("end letter") endLetter: Char
    ): ModelAndView {

        logger.info("/names/{start letter}/{end letter} accessed")

        val startLetterLC = startLetter.toLowerCase()
        val endLetterLC = endLetter.toLowerCase()

        val mav = ModelAndView()
        mav.viewName = "countriesStartEndName"

        val includedCountries = mutableListOf<Country>()
        mav.addObject("countryList", includedCountries)

        if (startLetterLC > endLetterLC) {
            return mav
        }

        Sprint12Application.countryList.data.forEach {
            val countryLetter = it.name?.get(0)?.toLowerCase()
            if (countryLetter != null) {
                if (countryLetter in startLetterLC..endLetterLC) {
                    includedCountries.add(it)
                }
            }
        }

        includedCountries.sortBy { it.name }

        return mav
    }

    // /gdp/list/{start gdp}/{end gdp}
    // inclusive
    @GetMapping("/gdp/list/{start gdp}/{end gdp}")
    fun displayCountriesStartEndGDP(
            @PathVariable("start gdp") startGDP: Long,
            @PathVariable("end gdp") endGDP: Long
    ): ModelAndView {

        logger.info("/gdp/list/{start gdp}/{end gdp} accessed")

        val mav = ModelAndView()
        mav.viewName = "countriesStartEndGDP"

        val includedCountries = mutableListOf<Country>()
        mav.addObject("countryList", includedCountries)

        if (startGDP > endGDP) {
            return mav
        }

        Sprint12Application.countryList.data.forEach {
            if (it.gdp in startGDP..endGDP) {
                includedCountries.add(it)
            }
        }

        includedCountries.sortBy { it.gdp }

        return mav
    }

}