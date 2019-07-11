package com.android.airmart.repository

import com.android.airmart.data.api.ChatApiService
import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatMessage
import com.android.airmart.data.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import javax.inject.Singleton

@Singleton
class ChatRepository  constructor( private val chatApiService: ChatApiService){
    suspend fun getAllChatsByUserAPI(token:String): Response<List<Chat>> =
        withContext(Dispatchers.IO){
            chatApiService.getAllChats(token).await()
        }
    suspend fun getAllMessagesInChatAPI(chatId:Long,token:String): Response<List<ChatMessage>> =
        withContext(Dispatchers.IO){
            chatApiService.getAllMessages(chatId,token).await()
        }

        suspend fun createNewChatAPI(sendTo:String,token:String): Response<Chat> =
            withContext(Dispatchers.IO){
                chatApiService.newChat(sendTo,token).await()
            }
    suspend fun createNewMessageAPI(chatId:Long,message:String,token:String): Response<ChatMessage> =
        withContext(Dispatchers.IO){
            chatApiService.newMessage(chatId,message,token).await()
        }
    suspend fun newMessage(chatId:Long,message:String,token:String):ChatMessage?=
        withContext(Dispatchers.IO){
            val res = createNewMessageAPI(chatId,message,token).body()
            if (res !=null){
                return@withContext ChatMessage(res.id,res.chat_id,res.message,res.username,res.avatar,res.postedDate)
            }
            return@withContext null
        }
    suspend fun newChat(sendTo:String,token:String)=
        withContext(Dispatchers.IO){
            createNewChatAPI(sendTo,token)
            return@withContext true
        }

    suspend fun getAllChatsByUser(token:String):List<Chat>? =
        withContext(Dispatchers.IO){
            var chatList = ArrayList<Chat>()
            try{
                var chats = getAllChatsByUserAPI(token).body()
                if(chats != null){
                    for (res in chats){
                        var chat= Chat(res.id,res.message,res.owner,res.postedDate,res.users,res.messages)
                        chatList.add(chat)
                    }
                    return@withContext chatList
                }

            }
            catch (e: ConnectException){
                return@withContext null
            }
            return@withContext null
        }

    suspend fun getAllMessagesInChat(chatId:Long,token:String):List<ChatMessage>? =
        withContext(Dispatchers.IO){
            var messageList = ArrayList<ChatMessage>()
            try{
                var messages = getAllMessagesInChatAPI(chatId,token).body()
                if(messages != null){
                    for (res in messages){
                        var message= ChatMessage(res.id,res.chat_id,res.message,res.username,res.avatar,res.postedDate)
                        messageList.add(message)
                    }
                    return@withContext messageList
                }

            }
            catch (e: ConnectException){
                return@withContext null
            }
            return@withContext null
        }

}
