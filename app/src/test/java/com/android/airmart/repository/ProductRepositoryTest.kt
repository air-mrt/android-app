package com.android.airmart.repository

import com.android.airmart.data.AppDatabase
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.entity.Product
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class ProductRepositoryTest {

    lateinit var product: Product

    @Before
    fun setup(){
        product = Product(1,"abebe","","","","","",1)

    }
    @Test
     fun deletproductbyIdtest(){
        val result = product.toString()
        assertFalse(result.isEmpty())
    }
}