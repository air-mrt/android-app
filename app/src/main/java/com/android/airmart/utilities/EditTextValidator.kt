package com.android.airmart.utilities

import android.widget.EditText

object EditTextValidator {
    fun isEmpty(editText: EditText):Boolean{
        val input = editText.text.toString()
        return input.length == 0
    }
}