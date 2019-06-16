package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.data.api.model.UserInfo
import com.android.airmart.data.entity.User
import com.android.airmart.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DashboardViewModel(private val userRepository: UserRepository): ViewModel()  {
    private  val _userInfoResponse = MutableLiveData<User>()
    val loginResponse: LiveData<User>?
        get() = _userInfoResponse

     fun getUserInfo(token:String)  =
         viewModelScope.launch(Dispatchers.IO) {
             _userInfoResponse.postValue(userRepository.getLoggedInUserInfo(token))
         }


}