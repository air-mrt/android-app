package com.android.airmart.EndtoEnd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ChatApiService
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.dao.ChatDao
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.repository.ChatRepository
import com.android.airmart.repository.ProductRepository
import com.android.airmart.utilities.testProducts
import com.android.airmart.viewmodel.ChatViewModel
import com.android.airmart.viewmodel.EditProductViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductviewmodelTest (){
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ChatViewModel
    private lateinit var chatDao: ProductDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        chatDao = appDatabase.productDao()

        chatDao = appDatabase.productDao()

        val productrepo = ProductRepository(chatDao,ProductApiService.getInstance())
        viewModel = EditProductViewModel(productrepo,1L)
    }
    @After
    fun tearDown() {
        appDatabase.close()
    }
    @Test
    fun deleterepoTest(){
        lateinit var result : Job
        runBlocking {
            result = viewModel.sendM(1,"message","token")
        }
    }
}