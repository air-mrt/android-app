package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.ProductModel
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PostProductViewModel (private val productRepository: ProductRepository, private val username:String) : ViewModel(){
    fun insertProduct (product: Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.insertProduct(product)
    }
    fun updateProduct (product: Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.updateProduct(product)
    }
    fun deleteProduct (product: Product) = viewModelScope.launch (Dispatchers.IO){
        productRepository.deleteProduct(product)
    }
    private  val _getResponse = MutableLiveData<Response<ProductModel>>()
    val getResponse: LiveData<Response<ProductModel>>
        get() = _getResponse

    private  val _postResponse = MutableLiveData<Response<ProductModel>>()
    val postResponse: LiveData<Response<ProductModel>>
        get() = _postResponse

    fun getProductById(id: Long) = viewModelScope.launch{
        _getResponse.postValue(productRepository.getProductByIdFromRetro(id))
    }

    fun postProduct(file: MultipartBody.Part, productJson: RequestBody, token:String) = viewModelScope.launch {
        _postResponse.postValue(productRepository.postProduct(file,productJson,token))
    }

}