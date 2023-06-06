package com.example.lifemate.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Helper {
    var uid = -1
    var token = ""
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun setDate(context: Context, edt:EditText){
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.UK)
        var calendar = Calendar.getInstance()

        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        val datepicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            edt.setText(sdf.format(calendar.time))
        }

        DatePickerDialog(context, datepicker, year, month, date).show()
    }

    fun getCurrentDate(): Calendar {
        return Calendar.getInstance()
    }

    fun getAge(birthDateStr: String): Int {
        val currentDate: Calendar = getCurrentDate()
        val birthDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        birthDate.time = dateFormat.parse(birthDateStr) as Date

        val years = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH)
        val birthMonth = birthDate.get(Calendar.MONTH)

        if (currentMonth < birthMonth) {
            return years - 1
        } else if (currentMonth == birthMonth) {
            val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
            val birthDay = birthDate.get(Calendar.DAY_OF_MONTH)

            if (currentDay < birthDay) {
                return years - 1
            }
        }

        return years
    }

    fun String.withDateFormat(): String {
        val originalFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate: Date = originalFormatter.parse(this) as Date

        val newFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return newFormatter.format(parsedDate)
    }

    fun String.withHistoryDayDateFormat(): String {
        val originalFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate: Date = originalFormatter.parse(this) as Date

        val newFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        return newFormatter.format(parsedDate)
    }

    fun String.withHistoryDateFormat(): String {
        val originalFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate: Date = originalFormatter.parse(this) as Date

        val newFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return newFormatter.format(parsedDate)
    }

    fun String.withHistoryDayFormat(): String {
        val originalFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val parsedDate: Date = originalFormatter.parse(this) as Date

        val newFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return newFormatter.format(parsedDate)
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun String.withHistoryDateFormat(): String {
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse(this , firstApiFormat)
        return date.dayOfWeek.toString()
    }*/

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun formatDate(date: String): String{
//        val formatter: DateTimeFormatter =
//            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CANADA_FRENCH)
//        val date: LocalDateTime = LocalDateTime.parse(date.toString(), formatter)
//        return date.toString()
//    }

}