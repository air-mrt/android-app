package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product
import javax.inject.Singleton

@Singleton
class ProductRepository constructor(private val productDao: ProductDao) {

    fun allProducts(): LiveData<List<Product>> = productDao.getAllProducts()
    fun insertProduct(product: Product) = productDao.insertProduct(product)
    fun updateProduct(product: Product) = productDao.updateProduct(product)
    fun deleteProduct(product: Product) = productDao.deleteProduct(product)
    fun getProductByTitle(title: String): LiveData<Product> = productDao.getProductByTitle(title)
    fun getProductById(productId: Int): LiveData<Product> = productDao.getProductById(productId)
}