package com.android.airmart

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.findNavController
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.repository.CommentRepository
import com.android.airmart.repository.ProductRepository
import com.android.airmart.ui.MainActivity
import com.android.airmart.utilities.testproduct
import com.android.airmart.viewmodel.PostDetailViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostDetailFragmentTest {
    private lateinit var appDatabase: AppDatabase
    lateinit var postDetailViewModel: PostDetailViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)
    @Before
    fun jumpToPlantDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                val bundle = Bundle().apply { putString("", testproduct.id.toString()) }
                findNavController(R.id.nav_fragment).navigate(R.id.postDetailFragment, bundle)
            }
        }
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

            val productrepo = ProductRepository(appDatabase.productDao(), ProductApiService.getInstance())
            val comrepo = CommentRepository(appDatabase.commentDao())
            postDetailViewModel = PostDetailViewModel(productrepo,comrepo,1)

    }

    @Test
    fun onaddcommentTest(){
        val text = "ggg"
        onView(withId(R.id.addbutton)).perform(click())
        Assert.assertEquals(activityTestRule.activity.isFinishing, false)
        postDetailViewModel.addComment(text,"user1")

    }

}