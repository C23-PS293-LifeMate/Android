package com.example.lifemate.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.lifemate.R

class ChangePwEditText: AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = if (s.length < 8) "Password length atleast 8 character length" else null
                background = if(s.length < 8){
                    ContextCompat.getDrawable(context, R.drawable.custom_error_edit_text)
                }else{
                    ContextCompat.getDrawable(context, R.drawable.custom_edit_text)
                }

            }
            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "New Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        maxLines = 1
    }

}