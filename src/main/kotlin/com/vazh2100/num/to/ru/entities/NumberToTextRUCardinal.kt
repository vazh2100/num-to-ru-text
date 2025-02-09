package com.vazh2100.num.to.ru.entities

import com.vazh2100.num.to.ru.entities.Cardinal.hundreds
import com.vazh2100.num.to.ru.entities.Cardinal.ones
import com.vazh2100.num.to.ru.entities.Cardinal.onesFem
import com.vazh2100.num.to.ru.entities.Cardinal.special
import com.vazh2100.num.to.ru.entities.Cardinal.tens
import com.vazh2100.num.to.ru.entities.Nums.HUNDRED
import com.vazh2100.num.to.ru.entities.Nums.MILLION
import com.vazh2100.num.to.ru.entities.Nums.TEN
import com.vazh2100.num.to.ru.entities.Nums.THOUSAND
import com.vazh2100.num.to.ru.entities.Nums.TWENTY

class NumberToTextRUCardinal : Converter {

    override fun convert(number: Int) = StringBuilder().convert(number).toString()

    private fun StringBuilder.convert(number: Int, step: Int = 0): StringBuilder {
        val num = number / THOUSAND
        val odd = number % THOUSAND
        if (num > 0) {
            convert(num, step + 1)
            convertOddIfNeeded(odd, step == 1)
        } else { // num == 0
            convertLess1000(odd, step == 1)
        }
        when (step) {
            1 -> if (odd > 0) append(odd.word(THOUSAND))
            2 -> append(odd.word(MILLION))
            3 -> error("Unsupported value")
        }
        return this
    }

    private fun StringBuilder.convertOddIfNeeded(odd: Int, feminine: Boolean = false): StringBuilder {
        if (odd == 0) return this
        return append(" ").convertLess1000(odd, feminine)
    }

    private fun StringBuilder.convertLess1000(number: Int, feminine: Boolean): StringBuilder {
        return when {
            number <= 2 -> if (feminine) append(onesFem[number]) else append(ones[number])
            number < TEN -> append(ones[number])
            number < TWENTY && number != TEN -> append(special[number % TEN])
            number < HUNDRED -> append(tens[number / TEN]).convertOddIfNeeded(number % TEN, feminine)
            else -> append(hundreds[number / HUNDRED]).convertOddIfNeeded(number % HUNDRED, feminine)
        }
    }
}
