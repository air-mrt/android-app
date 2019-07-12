package com.android.airmart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product
import com.android.airmart.utility.testProducts
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDaoTest {
    private lateinit var database:AppDatabase
    private lateinit var productDao: ProductDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase(){
        val context  = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        productDao = database.productDao()
        testProducts.forEach{ product->
            database.productDao().insertProduct(product)
        }
    }
    @After
    fun closeDatabase(){
        database.close()
    }
    @Test fun testGetAllProducts(){
        assertThat(productDao.getAllProducts().value?.size,equalTo(3))
    }
    @Test fun testGetProductByTitle(){
        assertThat(productDao.getProductByTitle("product1").value,equalTo(testProducts[0]))
    }
    @Test fun testInsertProduct(){
        productDao.insertProduct(
            Product(4, "product4", "product desc 4", "100","uri4","user4","",1)
        )
        assertThat(productDao.getAllProducts().value?.size,equalTo(4))
    }

    @Test fun testDeleteProduct(){
        productDao.deleteProduct(testProducts[0])
        assertThat(productDao.getAllProducts().value?.size,equalTo(2))

    }
}