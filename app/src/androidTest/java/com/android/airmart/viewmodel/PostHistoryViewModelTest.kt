package com.android.airmart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.api.UserApiService
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.dao.UserDao
import com.android.airmart.repository.ProductRepository
import com.android.airmart.repository.UserRepository
import com.android.airmart.utilities.testProducts
import com.android.airmart.utilities.testUser
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostHistoryViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: PostHistoryViewModel
    private lateinit var productDao: ProductDao
    private lateinit var userDao: UserDao

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
        userDao = appDatabase.userDao()
        testUser.forEach { User->
            appDatabase.userDao().insertUser(User)
        }

        val productRepo = ProductRepository(appDatabase.productDao(), ProductApiService.getInstance())
        val userrepo = UserRepository(appDatabase.userDao(), UserApiService.getInstance())
        viewModel = PostHistoryViewModel(productRepo,userrepo)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
    @Test
    @Throws(InterruptedException::class)
    fun testsefualtproduct() {
        assertThat(viewModel.allProductResponse.value?.size, CoreMatchers.equalTo(3))
    }
    @Test
    fun testGetproductbyUsername(){

    }
}
