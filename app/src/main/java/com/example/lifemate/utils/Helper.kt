package com.example.lifemate.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun setDate(context: Context, edt:EditText){
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        var calendar = Calendar.getInstance()

        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        val datepicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            edt.setText(sdf.format(calendar.time))
        }

        DatePickerDialog(context, datepicker, year, month, date).show()
    }

}