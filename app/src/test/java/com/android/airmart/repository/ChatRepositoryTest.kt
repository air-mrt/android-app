package com.android.airmart.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.dao.ChatDao
import com.android.airmart.data.entity.Chat
import com.android.airmart.utilities.testChat
import com.android.airmart.utilities.testChats
import org.junit.Before

class ChatRepositoryTest {
    lateinit var chat: Chat
    lateinit var chatDao:ChatDao
    private lateinit var appDatabase: AppDatabase
    @Before fun setUp(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        chat = testChat
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        chatDao = appDatabase.chatDao()
        testChats.forEach { chat->
            chatDao.send(chat)
        }
    }

}