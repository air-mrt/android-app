package com.android.airmart.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product

class ProductRepository private constructor(private val productDao: ProductDao){

    fun allProducts(): LiveData<List<Product>> = productDao.getAllProducts()
    fun insertProduct(product:Product) = productDao.insertProduct(product)
    fun updateProduct(product:Product) = productDao.updateProduct(product)
    fun deleteProduct(product:Product) = productDao.deleteProduct(product)
    fun getProductByTitle(title:String):LiveData<Product> = productDao.getProductByTitle(title)
    fun getProductById(productId:Int):LiveData<Product> = productDao.getProductById(productId)


companion object {

    // For Singleton instantiation
    @Volatile private var instance: ProductRepository? = null

    fun getInstance(productDao: ProductDao) =
        instance ?: synchronized(this) {
            instance ?: ProductRepository(productDao).also { instance = it }
        }
}
}