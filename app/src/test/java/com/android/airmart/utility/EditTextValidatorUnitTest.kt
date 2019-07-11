package com.android.airmart.utility

import android.widget.EditText
import com.android.airmart.R
import com.android.airmart.utilities.EditTextValidator
import org.junit.Before
import org.junit.Test
import java.nio.file.Files.find
import android.app.Activity
import com.android.airmart.ui.fragments.user.PostProductFragment
import org.junit.Assert.assertTrue


class EditTextValidatorUnitTest {
    private lateinit var editTextValidator:EditTextValidator
    lateinit var editText:EditText
    @Before
    fun createClassInstance(){
        editTextValidator=EditTextValidator
        editText = editText.findViewById(R.id.title_editText);
    }
    @Test
    fun isempty(){
        val text =editText.text.toString()
        val result=editTextValidator.isEmpty(editText)
        assertTrue(result)

    }
}