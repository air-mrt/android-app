package com.android.airmart.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.repository.UserRepository
import com.android.airmart.utilities.AuthenticationState
import com.android.airmart.utilities.SharedPrefUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException


class LoginViewModel(private val userRepository: UserRepository, context: Context):ViewModel() {

    val authenticationState = MutableLiveData<AuthenticationState>()
    init{
        val sharedPref = SharedPrefUtil.getSharedPref(context)
        if(SharedPrefUtil.isLoggedIn(sharedPref)){
            if (!SharedPrefUtil.isTokenExpired(sharedPref)){
                authenticationState.value = AuthenticationState.AUTHENTICATED
            }
            else{
                authenticationState.value = AuthenticationState.EXPIRED_TOKEN
            }
        }
        else{
            authenticationState.value = AuthenticationState.UNAUTHENTICATED
        }
    }
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
    fun refuseAuthentication(){
        authenticationState.value = AuthenticationState.INVALID_AUTHENTICATION
    }
    fun acceptAuthentication(){
        authenticationState.value = AuthenticationState.AUTHENTICATED
    }
    fun expiredAuthentication(){
        authenticationState.value = AuthenticationState.EXPIRED_TOKEN
    }


    }