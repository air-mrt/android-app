package com.android.airmart.data.api

import com.android.airmart.data.api.model.ProductModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ProductApiService {
    @GET("products/{id}")
    fun getProductById(@Path("id") id: Long): Deferred<Response<ProductModel>>
    companion object {

        private val baseUrl = "http://10.0.2.2:8080/api/"

        fun getInstance(): ProductApiService {



            val client = OkHttpClient
                .Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build()

            val retrofit: Retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retrofit.create(ProductApiService::class.java)


        }
    }
}