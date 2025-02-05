package com.vazh2100.num.to.ru.entities

import com.vazh2100.num.to.ru.entities.Nums.HUNDRED
import com.vazh2100.num.to.ru.entities.Nums.MILLION
import com.vazh2100.num.to.ru.entities.Nums.TEN
import com.vazh2100.num.to.ru.entities.Nums.THOUSAND

@Suppress("MagicNumber")
internal fun Int.word(digit: Int): String = when (val odd = this % HUNDRED) {
    1 -> when (digit) {
        THOUSAND -> " тысяча"
        MILLION -> " миллион"
        else -> error("Programmer error")
    }
    in 2..4 -> when (digit) {
        THOUSAND -> " тысячи"
        MILLION -> " миллиона"
        else -> error("Programmer error")
    }
    0, in 5..20 -> when (digit) {
        THOUSAND -> " тысяч"
        MILLION -> " миллионов"
        else -> error("Programmer error")
    }
    else -> (odd % TEN).word(digit)
}
