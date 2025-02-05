package com.vazh2100.num.to.ru

import com.vazh2100.num.to.ru.entities.NumberToTextRUCardinal
import com.vazh2100.num.to.ru.entities.NumberToTextRUOrdinal

class NumToRu(ordinal: Boolean = false) {

    private val converter = if (!ordinal) NumberToTextRUCardinal() else NumberToTextRUOrdinal()
    fun convert(num: Int) = converter.convert(num)
}
