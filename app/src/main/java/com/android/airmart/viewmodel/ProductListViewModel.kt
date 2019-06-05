package com.android.airmart.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel (private val productRepository: ProductRepository) : ViewModel(){
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