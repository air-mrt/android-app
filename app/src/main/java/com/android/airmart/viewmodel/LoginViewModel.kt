package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class LoginViewModel(private val userRepository: UserRepository):ViewModel() {
    private  val _getResponse = MutableLiveData<Response<LoginResponse>>()
    val getResponse: LiveData<Response<LoginResponse>>
        get() = _getResponse
    private val _insertResponse = MutableLiveData<Response<LoginResponse>>()
    val insertResponse: LiveData<Response<LoginResponse>>
        get() = _insertResponse

    fun login(authBody: AuthBody){
        viewModelScope.launch (Dispatchers.IO){
        _getResponse.postValue(userRepository.login(authBody))
        }


    }

}