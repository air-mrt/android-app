package com.android.airmart.repository

import com.android.airmart.data.entity.Chat
import org.junit.Before

class ChatUnitTest (){
    lateinit var chat
    @Before
    fun setup(){
        chat = Chat(1,"","","","","message")
    }
}