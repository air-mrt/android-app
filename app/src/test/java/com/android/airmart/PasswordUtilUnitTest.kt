package com.android.airmart

import com.android.airmart.utilities.PasswordUtil
import org.junit.Before
import org.junit.Test

class PasswordUtilUnitTest {
    private lateinit var passwordUtil: PasswordUtil

    @Before
    fun createClassInstance(){
        passwordUtil = PasswordUtil
    }

    @Test
    fun encryptPassword(){
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