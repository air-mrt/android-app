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
        allProducts = productRepository.allProductsRoom()
    }
    fun insertProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.insertProductRoom(product)
    }
    fun updateProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.updateProductRoom(product)
    }
    fun deleteProduct (product:Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.deleteProductRoom(product)
    }

}