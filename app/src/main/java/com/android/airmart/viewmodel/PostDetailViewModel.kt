

package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.entity.Comment
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.CommentRepository
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PostDetailViewModel(
    productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val productId: Long
) : ViewModel() {

    val product: LiveData<Product>
    val commentsForProduct:LiveData<List<Comment>>


    init {
        product = productRepository.getProductByIdRoom(productId)
        commentsForProduct = commentRepository.allCommentsbyProductId(productId)
    }

    fun addComment(content:String, username:String) {
        viewModelScope.launch (Dispatchers.IO){
        commentRepository.insertComment(
            Comment(0,productId,content,username,"null")
        )

    }}

}
