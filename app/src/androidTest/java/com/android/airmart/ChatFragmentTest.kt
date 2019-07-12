package com.android.airmart

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.findNavController
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ChatApiService
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.entity.Chat
import com.android.airmart.repository.ChatRepository
import com.android.airmart.repository.CommentRepository
import com.android.airmart.repository.ProductRepository
import com.android.airmart.ui.MainActivity
import com.android.airmart.utilities.testproduct
import com.android.airmart.viewmodel.ChatViewModel
import com.android.airmart.viewmodel.PostDetailViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class ChatFragmentTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var chatViewModel: ChatViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToChatFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                //val bundle = Bundle().apply { putString("productId", testproduct.id.toString()) }
                findNavController(R.id.nav_fragment).navigate(R.id.chatDialogFragment)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        val chatRepo = ChatRepository(ChatApiService.getInstance())
       chatViewModel = ChatViewModel(chatRepo)

    }
    @Test
    fun clickonChatDialog(){
        val chats =ArrayList<Chat>()
        chats.add(Chat(1,"message","owner","data"))
        Mockito.`when`(chatViewModel.dialogResponse)
            .thenReturn()
    }
}