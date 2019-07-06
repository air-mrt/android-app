package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.ProductResponse
import com.android.airmart.data.entity.Product
import com.android.airmart.data.entity.User
import com.android.airmart.repository.ProductRepository
import com.android.airmart.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException

class PostHistoryViewModel (private val productRepository: ProductRepository, private val userRepository: UserRepository) : ViewModel(){
    private  val _allProductResponse = MutableLiveData<List<Product>>()
    val allProductResponse: LiveData<List<Product>>
        get() = _allProductResponse

    private  val _deleteResponse = MutableLiveData<Boolean>()
    val deleteResponse: LiveData<Boolean>
        get() = _deleteResponse
    private  val _userInfoResponse = MutableLiveData<User>()
    val userInfoResponse: LiveData<User>?
        get() = _userInfoResponse

    fun getUserInfo(token:String, username:String)  =
        viewModelScope.launch(Dispatchers.IO) {
            _userInfoResponse.postValue(userRepository.getLoggedInUserInfo(token,username))
        }

    fun getAllProductsByUsername(username:String) = viewModelScope.launch{
        _allProductResponse.postValue(productRepository.allProductsByUser(username))
    }
    fun deletePostById(productId:Long,token:String) = viewModelScope.launch{
        try {
            _deleteResponse.postValue(productRepository.deleteProductById(productId,token))
        }
        catch (e:ConnectException){
            this.coroutineContext.cancel()
        }
}
    fun searchProduct(username:String, query:String) = productRepository.searchProductByUsername(username,query)
}