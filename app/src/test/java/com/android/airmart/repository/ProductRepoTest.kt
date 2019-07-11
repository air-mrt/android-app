package com.android.airmart.repository

import com.android.airmart.data.entity.Product
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


class ProductRepoTest {
    lateinit var productRepository: ProductRepository
    lateinit var product: Product

    @Before
    fun setup(){
        product = Product(1,"abebe","","","","","",1)
    }
    @Test
    suspend fun deletproductbyIdtest(){
        val productRepository =productRepository.deleteProductById(product.id,"")
        assertTrue(productRepository.toString().isEmpty())

    }
}