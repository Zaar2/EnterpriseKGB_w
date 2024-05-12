package com.zaar2.meatKGB_w.utilities.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.widget.Button
import android.widget.TextView
import com.zaar2.meatKGB_w.R
import java.util.Calendar

internal class UtilitiesView {

    /**
     * @param button will be block,
     * background tint for this button will be replace with 'R.color.button_blocked'
     */
    @Deprecated(
        "this method is deprecated",
        ReplaceWith(
            "activeBtnOff(" +
                    "button: Button?, " +
                    "backgroundTintColorState: ColorStateList," +
                    " textColorStateList: ColorStateList,)"
        )
    )
    fun activeBtnOffBlock(button: Button?, context: Context) {
        activeBtnOff(
            button,
            context.getColorStateList(R.color.tint_button_blocked),
            context.getColorStateList(R.color.black)
        )
    }

    /**
     * @param button this button will be block
     * @param backgroundTintColorState tint for button will be replace with this
     * @param textColorStateList text color for button will be replace with this
     */
    fun activeBtnOff(
        button: Button?,
        backgroundTintColorState: ColorStateList,
        textColorStateList: ColorStateList, ) {
        button?.apply {
            isEnabled = false
            backgroundTintList = backgroundTintColorState
            setTextColor(textColorStateList)
            refreshDrawableState()
        }
    }

    /**
     * button will be unblock,
     * background tint for this button will be replace with 'R.color.bg_selector',
     * and text color will be replace with 'R.color.milk_background'
     */
    @Deprecated(
        "this method is deprecated",
        ReplaceWith("activeBtnOn(button: Button?, colorStateList: ColorStateList, context: Context)")
    )
    fun activeBtnOn(button: Button?, context: Context) {
        activeBtnOn(
            button,
            context.getColorStateList(R.color.tint_bg_selector),
            context.getColorStateList(R.color.milk_background)
            )
    }

    /**
     * @param button will be unblock,
     * @param backgroundTintColorState background tint for this button will be replace with 'backgroundTintColorState: ColorStateList',
     * @param textColorStateList text color will be replace with 'textColorStateList: ColorStateList'
     */
    fun activeBtnOn(button: Button?, backgroundTintColorState: ColorStateList, textColorStateList: ColorStateList) {
        button?.apply {
            isEnabled = true
            backgroundTintList = backgroundTintColorState
            setTextColor(textColorStateList)
            refreshDrawableState()
        }
    }

    fun callDatePicker(textView: TextView, context: Context) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            R.style.my_dialog_theme,
            { _, y, m, dayOfMonth ->
                val selectedDate: String =
                    dateFormatIntToString(dayOfMonth)
                        .plus("." + dateFormatIntToString(m + 1))
                        .plus(".$y")
                textView.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    /**
     * вставляет в textView дату в нужном для этого приложения формате
     *
     * @param textView куда вставить
     * @param value    кол-во дней, которые нужно добавить к текущей дате
     *                 (может быть положительным/увеличение или отрицательным/уменьшение)
     */
    fun initDateDefaultForTextView(textView: TextView, value: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, value)
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH) + 1
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val date: String =
            dateFormatIntToString(day)
                .plus("." + dateFormatIntToString(month))
                .plus(".$year")
        textView.text = date
    }

    private fun dateFormatIntToString(num: Int): String {
        return if (num in 0..9) {
            "0$num"
        } else if (num < 0 && num > -10) {
            "-0" + (num * (-1))
        } else
            num.toString()
    }

    inline fun <reified T> addToBeginOfArr(incomeArr: Array<T>, element: T): Array<T> {
        return Array(incomeArr.size + 1) { i ->
            if (i == 0) element
            else {
                incomeArr[i - 1]
            }
        }
    }
}