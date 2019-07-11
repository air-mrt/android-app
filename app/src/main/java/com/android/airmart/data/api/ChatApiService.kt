package com.android.airmart.data.api


import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatMessage
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

interface ChatApiService {
    @POST("chat/auth/chats")
    fun newChat(@Query("send_to") username: String,@Header("Authorization") token:String): Deferred<Response<Chat>>
    @GET("chat/auth/chats")
    fun getAllChats(@Header("Authorization")token:String): Deferred<Response<List<Chat>>>
    @GET("chat/auth/messages/{id}")
    fun getAllMessages(@Path("id")chatId:Long, @Header("Authorization")token:String): Deferred<Response<List<ChatMessage>>>

    @POST("chat/auth/messages/{id}")
    fun newMessage(@Path("id")chatId:Long,@Query("message") username: String,@Header("Authorization") token:String): Deferred<Response<ChatMessage>>


    companion object {

        fun getInstance():ChatApiService {
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
            return retrofit.create(ChatApiService::class.java)


        }
    }
}