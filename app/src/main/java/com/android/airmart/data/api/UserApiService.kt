package com.android.airmart.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("login")
    fun LoginAsync(@Body authBody: AuthBody): Deferred<Response<LoginResponse>>

    companion object {

        private val baseUrl = "http://localhost:8080/api/"

        fun getInstance(): UserApiService {



            val client = OkHttpClient
                .Builder()
                .build()

            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retrofit.create(UserApiService::class.java)


        }
    }
}