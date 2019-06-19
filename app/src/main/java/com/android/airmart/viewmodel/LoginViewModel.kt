package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException


class LoginViewModel(private val userRepository: UserRepository):ViewModel() {
    private  val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse: LiveData<Response<LoginResponse>>
        get() = _loginResponse
    fun login(authBody: AuthBody) =
        viewModelScope.launch (Dispatchers.IO){
            try{
                _loginResponse.postValue(userRepository.login(authBody))
            }

            catch (e: ConnectException){
                this.coroutineContext.cancel()
            }
        }


    }