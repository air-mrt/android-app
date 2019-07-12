package com.android.airmart

import com.android.airmart.data.dao.ChatDao
import com.android.airmart.data.dao.ChatDao_Impl
import com.android.airmart.data.dao.MessageDao
import com.android.airmart.data.dao.MessageDao_Impl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import com.android.airmart.utility.testChat
import org.junit.Assert

class ChatUnitTest {
    lateinit var chatDao: ChatDao
    lateinit var messageDao: MessageDao

    @Before
    fun setup(){
        chatDao = Mockito.mock(ChatDao_Impl::class.java)
        messageDao = Mockito.mock(MessageDao_Impl::class.java)
    }
    @Test
    fun sendMessage(){
        val result = chatDao.send(testChat)
        Assert.assertEquals(chatDao.send(testChat), 1L)
    }
    @Test
    fun loadChatDialogs(){

    }
    @Test
    fun loadMessages(){

    }
}