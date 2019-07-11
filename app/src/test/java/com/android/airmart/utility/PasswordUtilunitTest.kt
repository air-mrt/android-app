package com.android.airmart.utility

import com.android.airmart.utilities.PasswordUtil
import org.junit.Before
import org.junit.Test

class PasswordUtilunitTest {
    private lateinit var passwordUtil: PasswordUtil

    @Before
    fun createClassInstance(){
        passwordUtil = PasswordUtil
    }

    @Test
    fun encryptPasswordw(){
        val password = "banana"
        val result = passwordUtil.encrypt(password)
        assert(result != password)
    }
    @Test
    fun decryptPassword(){
        val password = "banana"
        val encryptedPassword = passwordUtil.encrypt(password)
        val result = passwordUtil.decrypt(encryptedPassword)
        assert(result == password)
    }
}