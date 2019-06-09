package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.data.api.UserApiService
import com.android.airmart.data.dao.UserDao
import com.android.airmart.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UserRepository constructor(private val userDao: UserDao, private val userApiService: UserApiService) {

    suspend fun login(authBody: AuthBody): Response<LoginResponse> =
        withContext(Dispatchers.IO){
        userApiService.LoginAsync(authBody = authBody).await()
        }
    fun allUsers(): LiveData<List<User>> = userDao.getAllUsers()
    fun insertUser(product: User) = userDao.insertUser(product)
    fun updateUser(product: User) = userDao.updateUser(product)
    fun deleteUser(product: User) = userDao.deleteUser(product)
    fun getUserByUsername(username: String): LiveData<User> = userDao.getUsersByUsername(username)
}