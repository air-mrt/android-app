package com.android.airmart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.dao.CommentDao
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.repository.CommentRepository
import com.android.airmart.repository.ProductRepository
import com.android.airmart.utilities.testComments
import com.android.airmart.utilities.testProducts
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDetailViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: PostDetailViewModelTest
    private lateinit var productDao: ProductDao
    private lateinit var commentDao: CommentDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDao = appDatabase.productDao()
        testProducts.forEach{ product->
            appDatabase.productDao().insertProduct(product)
        }
        commentDao = appDatabase.commentDao()
        testComments.forEach { comment->
            appDatabase.commentDao().insertComment(comment)
        }

        val productRepo = ProductRepository(appDatabase.productDao(), ProductApiService.getInstance())
        val commentrepo = CommentRepository(appDatabase.commentDao())
        viewModel = PostDetailViewModelTest()

    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
    @Test
    fun somethod(){
        assertTrue(viewModel.toString().isEmpty())
    }
}