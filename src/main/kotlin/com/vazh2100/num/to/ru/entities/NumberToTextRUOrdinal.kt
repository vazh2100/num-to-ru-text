package com.vazh2100.num.to.ru.entities

import com.vazh2100.num.to.ru.entities.Genitive.HUNDRED_GEN
import com.vazh2100.num.to.ru.entities.Nums.BILLION
import com.vazh2100.num.to.ru.entities.Nums.HUNDRED
import com.vazh2100.num.to.ru.entities.Nums.MILLION
import com.vazh2100.num.to.ru.entities.Nums.TEN
import com.vazh2100.num.to.ru.entities.Nums.THOUSAND
import com.vazh2100.num.to.ru.entities.Nums.TWENTY
import com.vazh2100.num.to.ru.entities.Ordinal.MILLIONTH
import com.vazh2100.num.to.ru.entities.Ordinal.THOUSANDTH

internal class NumberToTextRUOrdinal : Converter {

    override fun convert(number: Int) = StringBuilder().convert(number).toString()

    private fun StringBuilder.convert(number: Int, feminine: Boolean = false, ordinal: Boolean = false): StringBuilder {
        return when {
            number <= 2 -> when {
                feminine -> append(Cardinal.onesFem[number])
                ordinal -> append(Cardinal.ones[number])
                else -> append(Ordinal.ones[number])
            }
            number < TEN -> when {
                ordinal -> append(Cardinal.ones[number])
                else -> append(Ordinal.ones[number])
            }
            number == TEN -> when {
                ordinal -> append(Cardinal.tens[1])
                else -> append(Ordinal.tens[1])
            }
            number < TWENTY -> when {
                ordinal -> append(Cardinal.special[number % TEN])
                else -> append(Ordinal.specials[number % TEN])
            }
            number < HUNDRED -> {
                val tens = number / TEN
                val odd = number % TEN
                when {
                    ordinal && odd == 0 -> append(Cardinal.tens[tens])
                    ordinal -> append(Cardinal.tens[tens]).convertOdd(odd, feminine, true)
                    odd == 0 -> append(Ordinal.tens[tens])
                    else -> append(Cardinal.tens[tens]).convertOdd(odd, feminine, false)
                }
            }
            number < THOUSAND -> {
                val hundreds = number / HUNDRED
                when (val odd = number % HUNDRED) {
                    0 -> append(Ordinal.hundreds[hundreds])
                    else -> append(Cardinal.hundreds[hundreds]).convertOdd(odd, feminine, ordinal)
                }
            }
            number < MILLION -> {
                val thousands = number / THOUSAND
                val odd = number % THOUSAND
                if (odd == 0) {
                    convertGen(thousands).append(THOUSANDTH)
                } else {
                    convert(thousands, thousands % TEN <= 2, true)
                        .append(thousands.word(THOUSAND))
                        .convertOdd(odd, feminine)
                }
            }
            number < BILLION -> {
                val millions = number / MILLION
                val odd = number % MILLION
                if (odd == 0) {
                    convertGen(millions).append(MILLIONTH)
                } else {
                    convert(millions, feminine = false, ordinal = true)
                        .append(millions.word(MILLION))
                        .convertOdd(odd, feminine)
                }
            }
            else -> error("Unsupported value")
        }
    }

    private fun StringBuilder.convertOdd(odd: Int, feminine: Boolean = false, ordinal: Boolean = false): StringBuilder {
        if (odd == 0) return this
        return append(" ").convert(odd, feminine, ordinal)
    }

    private fun StringBuilder.convertGen(number: Int): StringBuilder {
        return when {
            number < TEN -> append(Genitive.ones[number])
            number == TEN -> append(Genitive.tens[1])
            number < TWENTY -> append(Genitive.specials[number % TEN])
            number < HUNDRED -> {
                val tens = number / TEN
                val odd = number % TEN
                if (odd == 0) {
                    append(Genitive.tens[tens])
                } else {
                    append(Genitive.tens[tens]).convertOddGen(odd)
                }
            }
            number < THOUSAND -> {
                val hundreds = number / HUNDRED
                val odd = number % HUNDRED
                if (odd == 0) {
                    convertGen(hundreds).append(HUNDRED_GEN)
                } else {
                    convertGen(hundreds).append(HUNDRED_GEN).convertOddGen(odd)
                }
            }
            else -> error("Unsupported value")
        }
    }

    private fun StringBuilder.convertOddGen(odd: Int): StringBuilder {
        if (odd == 0) return this
        return convertGen(odd)
    }
}
