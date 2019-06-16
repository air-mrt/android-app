package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.data.api.UserApiService
import com.android.airmart.data.api.model.UserInfo
import com.android.airmart.data.dao.UserDao
import com.android.airmart.data.entity.User
import com.bumptech.glide.Glide.init
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Singleton

@Singleton
class UserRepository constructor(private val userDao: UserDao, private val userApiService: UserApiService) {

    suspend fun login(authBody: AuthBody): Response<LoginResponse> =
        withContext(Dispatchers.IO){
        userApiService.Login(authBody.username,authBody.password).await()
        }
    suspend fun getLoggedInUserInfoAPI(token:String):Response<UserInfo> =
            withContext(Dispatchers.IO){
                userApiService.getLoggedInUserInfo(token).await()
            }
    suspend fun getLoggedInUserInfo(token: String):User? =
        withContext(Dispatchers.IO){
            try{
                val res = getLoggedInUserInfoAPI(token).body()
                lateinit var user:User
                if (res!=null){
                    user = User(res.username,res.name,res.phone,res.email,res.profilePicture,res.numberOfPosts)
                    userDao.insertUser(user)
                    return@withContext user
                }
            }
            catch (e: ConnectException){
                    //TODO fetch user info from room if there is any
                    return@withContext userDao.getUserByUsername("user3")
            }
            return@withContext null
        }
    fun allUsers(): LiveData<List<User>> = userDao.getAllUsers()
    fun insertUser(product: User) = userDao.insertUser(product)
    fun updateUser(product: User) = userDao.updateUser(product)
    fun deleteUser(product: User) = userDao.deleteUser(product)
}