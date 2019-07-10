package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.ProductResponse
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.net.ConnectException

class EditProductViewModel(private val productRepository: ProductRepository, private val productId: Long):ViewModel() {
    val product: LiveData<Product>

    init {
        product = productRepository.getProductById(productId)
    }

    private  val _editResponse = MutableLiveData<Response<ProductResponse>>()
    val editResponse: LiveData<Response<ProductResponse>>
        get() = _editResponse
    fun editProduct(file: MultipartBody.Part?, productJson: RequestBody, token:String) = viewModelScope.launch {
        try{
            _editResponse.postValue(productRepository.editProductAPI(productId,file,productJson,token))
        }
        catch (e: ConnectException){
            this.coroutineContext.cancel()
        }

    }
}