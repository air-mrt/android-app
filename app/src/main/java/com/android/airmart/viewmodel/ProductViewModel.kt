package com.android.airmartversion1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.airmartversion1.data.AirmartDatabase
import com.android.airmartversion1.data.entity.Product
import com.android.airmartversion1.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application){
    private val productRepository: ProductRepository = ProductRepository(application)
    val allProducts: LiveData<List<Product>>

    init {
        allProducts = productRepository.allProducts()
    }
    fun insertProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.insertProduct(product)
    }
    fun updateProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.updateProduct(product)
    }
    fun deleteProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.deleteProduct(product)
    }

}