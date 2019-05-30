

package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository



class PostDetailViewModel(
    productRepository: ProductRepository,
    private val productId: Int
) : ViewModel() {

    val product: LiveData<Product>


    init {
        product = productRepository.getProductById(productId)
    }

}
