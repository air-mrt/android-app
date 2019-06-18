package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.api.model.AuthBody
import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.data.api.UserApiService
import com.android.airmart.data.api.model.MessageResponse
import com.android.airmart.data.api.model.UserInfo
import com.android.airmart.data.dao.UserDao
import com.android.airmart.data.entity.User
import com.android.airmart.utilities.DEFAULT_VALUE_SHARED_PREF
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
    suspend fun validateTokenAPI(token:String):Response<MessageResponse> =
        withContext(Dispatchers.IO){
            userApiService.validateToken(token).await()
        }
    suspend fun getLoggedInUserInfo(token: String, username:String):User? =
        withContext(Dispatchers.IO){
            try{
                val res = getLoggedInUserInfoAPI(token).body()
                if (res!=null){
                    val user = User(res.username,res.name,res.phone,res.email,res.profilePicture,res.numberOfPosts)
                    userDao.insertUser(user)
                    return@withContext user
                }
            }
            catch (e: ConnectException){
                    if (username != DEFAULT_VALUE_SHARED_PREF){
                        return@withContext userDao.getUserByUsername(username)
                    }
                    //if there is no saved username in shared pref
                    return@withContext null

            }
            //some error case
            return@withContext null
        }
    suspend fun validateToken(token:String)=
            withContext(Dispatchers.IO){
                try {
                    val res = validateTokenAPI(token)
                        if (res.isSuccessful) return@withContext true
                        return@withContext false
                }
                catch (e:ConnectException){
                    return@withContext false
                }
            }
    fun allUsers(): LiveData<List<User>> = userDao.getAllUsers()
    fun insertUser(product: User) = userDao.insertUser(product)
    fun updateUser(product: User) = userDao.updateUser(product)
    fun deleteUser(product: User) = userDao.deleteUser(product)
}