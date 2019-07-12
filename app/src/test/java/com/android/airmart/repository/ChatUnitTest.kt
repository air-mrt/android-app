package com.android.airmart.repository

import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class ChatUnitTest (){
    lateinit var chat:ChatEntity
    @Before
    fun setup(){
        chat = ChatEntity(1,"","","","","")
    }
    @Test
    fun message(){
        val result = chat.toString()
        assertFalse(result.isEmpty())
    }
    @Test
    fun idofmesaage(){
        val result = chat.id
        assertEquals(result,1)
    }
}