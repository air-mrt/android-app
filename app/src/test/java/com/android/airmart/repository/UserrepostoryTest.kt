package com.android.airmart.repository

import com.android.airmart.data.entity.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserrepostoryTest (){
    lateinit var user:User
    @Before
    fun setup(){
        user = User("user1","kebde","09928202","hsihe2@","","1")
    }
    @Test
    fun insertuserTest() {
        val result = user.toString()
        assertFalse(result.toString().isEmpty())
    }



}