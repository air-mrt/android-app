package com.android.airmart.repository


import com.android.airmart.data.entity.Product

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test



class ProductRepositoryTest {

    lateinit var product: Product

    @Before
    fun setup(){
       product = Product(1,"user1","","","","","",1)


    }

    @Test
     fun deletproductbyIdtest(){
        val t = testpro[0]
        val minus = testpro.remove(t)
        assertEquals(1,testpro.size)
    }
    @Test
    fun insert (){
       val resuslt = testpro.add(product)
        assertTrue(resuslt)
    }

        val testpro = arrayListOf<Product>(
            Product(1,"user1","","","","","",1),
            Product(1,"user1","","","","","",1)
        )


}