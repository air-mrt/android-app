package com.android.airmart

import android.widget.EditText
import com.android.airmart.utilities.EditTextValidator
import org.junit.Before
import org.junit.Test

class EditTextValidatorUnitTest {
    private lateinit var editTextValidator:EditTextValidator
    lateinit var editText:EditText

    @Before
    fun createClassInstance(){
        editTextValidator=EditTextValidator
    }
    @Test
    fun isempty(){
        val text =editText.text.toString()
        val result=editTextValidator.isEmpty(editText)
        assert(!result.equals(text.length))

    }
}