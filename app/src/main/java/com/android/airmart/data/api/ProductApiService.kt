package com.android.airmart.data.api

import com.android.airmart.data.api.model.ProductResponse
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
interface ProductApiService {
    @GET("products/{id}")
    fun getProductById(@Path("id") id: Long): Deferred<Response<ProductResponse>>

    @GET("products/user")
    fun getAllProducts(@Header("Authorization") token:String): Deferred<Response<List<ProductResponse>>>
    @GET("products/interested")
    fun getAllInterestedProducts(@Header("Authorization") token:String): Deferred<Response<List<ProductResponse>>>
    @GET("products/search")
    fun searchProduct(@Query("keyword")keyword:String): Deferred<Response<List<ProductResponse>>>
    @DELETE("products/auth/{id}")
    fun deleteProductById(@Path("id")id: Long,
                          @Header("Authorization") token:String): Deferred<Response<Void>>
    @Multipart
    @POST("products/auth")
    fun postProduct(@Part file: MultipartBody.Part?,
                    @Part("productJson") productJson: RequestBody,
                    @Header("Authorization") token:String): Deferred<Response<ProductResponse>>
    @POST("products/interested/{id}")
    fun interested(@Path("id") id:Long,
                    @Header("Authorization") token:String): Deferred<Response<ProductResponse>>
    @POST("products/uninterested/{id}")
    fun uninterested(@Path("id") id:Long,
                   @Header("Authorization") token:String): Deferred<Response<ProductResponse>>

    @Multipart
    @PATCH("products/auth/{id}")
    fun editProduct(@Path("id")id:Long,@Part file: MultipartBody.Part?,
                    @Part("productJson") productJson: RequestBody,
                    @Header("Authorization") token:String): Deferred<Response<ProductResponse>>

    companion object {
        private val baseUrl = LOCALHOST_BASE_URL
        fun getInstance(): ProductApiService {
            val client = OkHttpClient
                .Builder()
                .connectTimeout(API_CONNECT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(API_READ_TIMEOUT,TimeUnit.SECONDS)
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