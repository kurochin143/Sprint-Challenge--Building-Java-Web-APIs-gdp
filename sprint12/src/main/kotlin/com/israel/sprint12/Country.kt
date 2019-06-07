package com.israel.sprint12

import java.util.concurrent.atomic.AtomicLong

class Country {

    var id = 0L
    var name: String? = null
    var gdp: Long = 0L

    companion object {
        val counter = AtomicLong()
    }

    constructor()

    constructor(name: String?, gdp: String?) {
        id = counter.incrementAndGet()
        this.name = name
        this.gdp = gdp?.toLong() ?: 0L
    }

}