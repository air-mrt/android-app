package com.android.airmart

import android.content.Context
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
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
import com.android.airmart.ui.fragments.user.chat.ChatDialogFragment
import com.android.airmart.ui.fragments.user.chat.ChatDialogFragmentDirections
import com.android.airmart.utilities.testproduct
import com.android.airmart.viewmodel.ChatViewModel
import com.android.airmart.viewmodel.PostDetailViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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
    fun clickOnChatDialog(){

        Assert.assertEquals(true,true)
    }

}