package com.android.airmart.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.ConnectException

class ProductListViewModel (private val productRepository: ProductRepository) : ViewModel(){
    val allProducts: LiveData<List<Product>>

    init {
        allProducts = productRepository.allProductsRoom()
    }

    private  val _searchResultResponse = MutableLiveData<List<Product>>()
    val searchResultResponse: LiveData<List<Product>>
        get() = _searchResultResponse

    fun search(keyword:String) = viewModelScope.launch{
        _searchResultResponse.postValue(productRepository.searchResult(keyword))
    }

    private  val _interestedResponse = MutableLiveData<Boolean>()
    val interestedResponse: LiveData<Boolean>
        get() = _interestedResponse
    fun interested(productId:Long,token:String) = viewModelScope.launch{
        try {
            _interestedResponse.postValue(productRepository.interested(productId,token))
        }
        catch (e: ConnectException){
            this.coroutineContext.cancel()
        }
    }


}