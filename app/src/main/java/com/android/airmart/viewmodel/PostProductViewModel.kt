package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.ProductResponse
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.net.ConnectException

class PostProductViewModel (private val productRepository: ProductRepository) : ViewModel(){
    private  val _postResponse = MutableLiveData<Response<ProductResponse>>()
    val postResponse: LiveData<Response<ProductResponse>>
        get() = _postResponse
    fun postProduct(file: MultipartBody.Part?, productJson: RequestBody, token:String) = viewModelScope.launch {
        try{
            _postResponse.postValue(productRepository.postProductAPI(file,productJson,token))
        }
        catch (e:ConnectException){
            this.coroutineContext.cancel()
        }

    }

}