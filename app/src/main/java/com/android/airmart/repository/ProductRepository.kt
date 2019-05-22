package com.android.airmartversion1.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.airmartversion1.data.AirmartDatabase
import com.android.airmartversion1.data.dao.ProductDao
import com.android.airmartversion1.data.entity.Product

class ProductRepository(private val application: Application){
    private  var airmartDatabase: AirmartDatabase = AirmartDatabase.getDatabase(application)
    private  var productDao: ProductDao
    init{
        productDao = airmartDatabase.productDao()
    }
    fun allProducts(): LiveData<List<Product>> = productDao.getAllProducts()
    fun insertProduct(product:Product) = productDao.insertProduct(product)
    fun updateProduct(product:Product) = productDao.updateProduct(product)
    fun deleteProduct(product:Product) = productDao.deleteProduct(product)
    fun getProductByTitle(title:String):Product = productDao.getProductByTitle(title)
}