package com.android.airmart.EndtoEnd

import android.os.Message
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.observe
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ChatApiService
import com.android.airmart.data.dao.ChatDao
import com.android.airmart.data.entity.ChatMessage
import com.android.airmart.repository.ChatRepository
import com.android.airmart.viewmodel.ChatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
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
        lateinit var result :Job
        runBlocking {
            result = viewModel.sendMessage(1,"message","token")
        }
        Assert.assertNotEquals(null,result)
    }
}

