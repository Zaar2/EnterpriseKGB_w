package com.zaar.meatkgb2_w.utilities.view

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log10

class UtilitiesTextFormat {
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

    fun convertDateServToLocal(servDate: String): String? =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(servDate)?.let { date ->
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }
}