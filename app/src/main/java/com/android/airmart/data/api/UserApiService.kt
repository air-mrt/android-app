package com.android.airmart.data.api

import com.android.airmart.data.api.model.LoginResponse
import com.android.airmart.data.api.model.UserInfo
import com.android.airmart.utilities.API_CONNECT_TIMEOUT
import com.android.airmart.utilities.API_READ_TIMEOUT
import com.android.airmart.utilities.LOCALHOST_BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UserApiService {
    @POST("login")
    fun Login(@Query("username") username: String,@Query("password") password: String): Deferred<Response<LoginResponse>>
    @Multipart
    @POST("register")
    fun postProduct(@Part("image") file: MultipartBody.Part,
                    @Part("userJson") productJson: RequestBody
                   ): Deferred<Response<Void>>
    @GET("users/info")
    fun getLoggedInUserInfo(@Header("Authorization") token:String):Deferred<Response<UserInfo>>

    companion object {

        fun getInstance(): UserApiService {
            val client = OkHttpClient
                .Builder()
                .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(LOCALHOST_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(UserApiService::class.java)


        }
    }
}