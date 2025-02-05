package com.vazh2100.num.to.ru.entities

import com.vazh2100.num.to.ru.entities.Cardinal.hundreds
import com.vazh2100.num.to.ru.entities.Cardinal.ones
import com.vazh2100.num.to.ru.entities.Cardinal.onesFem
import com.vazh2100.num.to.ru.entities.Cardinal.special
import com.vazh2100.num.to.ru.entities.Cardinal.tens
import com.vazh2100.num.to.ru.entities.Nums.BILLION
import com.vazh2100.num.to.ru.entities.Nums.HUNDRED
import com.vazh2100.num.to.ru.entities.Nums.MILLION
import com.vazh2100.num.to.ru.entities.Nums.TEN
import com.vazh2100.num.to.ru.entities.Nums.THOUSAND
import com.vazh2100.num.to.ru.entities.Nums.TWENTY

class NumberToTextRUCardinal : Converter {

    override fun convert(number: Int) = StringBuilder().convert(number).toString()

    private fun StringBuilder.convert(number: Int, feminine: Boolean = false): StringBuilder {
        return when {
            number <= 2 -> when {
                feminine -> append(onesFem[number])
                else -> append(ones[number])
            }
            number < TEN -> append(ones[number])
            number == TEN -> append(tens[1])
            number < TWENTY -> append(special[number % TEN])
            number < HUNDRED -> append(tens[number / TEN]).convertOdd(number % TEN, feminine)
            number < THOUSAND -> append(hundreds[number / HUNDRED]).convertOdd(number % HUNDRED, feminine)
            number < MILLION -> {
                val thousands = number / THOUSAND
                val odd = number % THOUSAND
                convert(thousands, thousands % TEN <= 2).append(thousands.word(THOUSAND)).convertOdd(odd, feminine)
            }
            number < BILLION -> {
                val millions = number / MILLION
                val odd = number % MILLION
                convert(millions).append(millions.word(MILLION)).convertOdd(odd, feminine)
            }
            else -> error("Unsupported value")
        }
    }

    private fun StringBuilder.convertOdd(odd: Int, feminine: Boolean = false): StringBuilder {
        if (odd == 0) return this
        return append(" ").convert(odd, feminine)
    }
}
