package com.android.airmart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.dao.ChatDao
import com.android.airmart.utilities.testChat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ChatDaoTest{
    private lateinit var database:AppDatabase
    private lateinit var chatDao: ChatDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDatabase(){
        val context  = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        chatDao = database.chatDao()
    }
    @After fun closeDatabase(){
        database.close()
    }

    @Test fun testInsertComment(){
        chatDao.send(testChat)
        assertThat(chatDao.getAll().id, equalTo(1L))
    }


}