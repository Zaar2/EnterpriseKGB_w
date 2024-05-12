package com.zaar2.meatKGB_w.utilities.view

import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log10

class UtilitiesTextFormat {
    fun convertDateServToLocal(servDate: String): String? =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(servDate)?.let { date ->
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }

    /**
     * ru - если значение числа меньше 10, то формат возвращенного строкового представления '00'
     *
     * eng - if the value of the number is less than 10, then the format of the returned string representation is '00'
     */
    fun dateFormatIntToStr(num: Int): String {
        return when (num) {
            in 0..9 -> "0$num"
            in -9 until 0 -> "-0" + num * -1
            else -> num.toString()
        }
    }

    /**
     * @return number of symbols in the input digit
     *
     * so far, this class is not using in the app
     */
    fun <T : CharSequence> findingNumberOfDecimalPlaces(num: T): Int =
        findingNumberOfDecimalPlaces(num.toString().toDouble())

    fun <T : Number> findingNumberOfDecimalPlaces(num: T): Int {
        val result = separationOfRemain(num.toDouble())
        return if (result <= 0) 0 else result.toString().length
    }

    private fun separationOfRemain(someNumber: Double): Int {
        return someNumber.toString().substring(
            getCountsOfDigits(someNumber.toInt().toLong()) + 1
        ).toInt()
    }

    private fun getCountsOfDigits(number: Long): Int {
        return if (number == 0L) 1 else ceil(log10(abs(number) + 0.5)).toInt()
    }
}