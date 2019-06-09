package com.android.airmart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.repository.ProductRepository
import com.android.airmart.utilities.testProducts
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductListViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ProductListViewModel
    private lateinit var productDao: ProductDao

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

        val productRepo = ProductRepository(appDatabase.productDao(), ProductApiService.getInstance())
        viewModel = ProductListViewModel(productRepo)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        assertThat(viewModel.allProducts.value?.size,equalTo(3))
    }
}