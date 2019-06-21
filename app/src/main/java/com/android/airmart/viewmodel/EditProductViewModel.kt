package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository

class EditProductViewModel(productRepository: ProductRepository, productId: Long):ViewModel() {
    val product: LiveData<Product>

    init {
        product = productRepository.getProductById(productId)
    }
}