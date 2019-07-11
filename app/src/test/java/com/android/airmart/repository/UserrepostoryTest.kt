package com.android.airmart.repository

import com.android.airmart.data.entity.User
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserrepostoryTest (){
    lateinit var user:User
    lateinit var userRepository: UserRepository
    @Before
    fun setup(){
        user = User("user1","kebde","09928202","hsihe2@","","1")
    }
    @Test
    fun insertuserTest(){
        val userRepository = userRepository.insertUser(user)
        assertEquals(user,userRepository)
    }
    @Test
    fun deletUserTest(){
        val userRepository = userRepository.deleteUser(user)
        assertEquals(user.toString(),userRepository.toString())
    }


}