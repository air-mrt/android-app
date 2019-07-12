package com.android.airmart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ChatApiService
import com.android.airmart.data.dao.ChatDao
import com.android.airmart.repository.ChatRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class ChatViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ChatViewModel
    private lateinit var chatDao: ChatDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        chatDao = appDatabase.chatDao()

        chatDao = appDatabase.chatDao()

        val chatRepo = ChatRepository(ChatApiService.getInstance())
        viewModel = ChatViewModel(chatRepo)

    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun sendMessage(){
        Mockito.`when`(viewModel.sendMessage(1,"message","token"))
            .thenReturn(Job())
        runBlocking {
            viewModel.sendMessage(1,"message","token")
        }
    }
}