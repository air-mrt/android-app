package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatMessage
import com.android.airmart.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel (private val chatRepository: ChatRepository): ViewModel(){
    private  val _dialogResponse = MutableLiveData<List<Chat>>()
    val dialogResponse: LiveData<List<Chat>>?
        get() = _dialogResponse

    fun getallChats(token:String)  =
        viewModelScope.launch(Dispatchers.IO) {
            _dialogResponse.postValue(chatRepository.getAllChatsByUser(token))
        }


    private val _messageResponse = MutableLiveData<List<ChatMessage>>()
    val messageResponse: LiveData<List<ChatMessage>>?
        get() = _messageResponse

    fun getallMessagesInChats(chatId:Long,token:String)  =
        viewModelScope.launch(Dispatchers.IO) {
            _messageResponse.postValue(chatRepository.getAllMessagesInChat(chatId,token))
        }

    private val _sendmessageResponse = MutableLiveData<Boolean>()
    val sendmessageResponse: LiveData<Boolean>?
        get() = _sendmessageResponse

    fun sendMessage(chatId:Long,message: String,token:String)  =
        viewModelScope.launch(Dispatchers.IO) {
            _sendmessageResponse.postValue(chatRepository.newMessage(chatId,message,token))
        }


    private val _newDialogResponse = MutableLiveData<Boolean>()
    val newDialogResponse: LiveData<Boolean>?
        get() = _newDialogResponse

    fun createNewDialog(sendTo: String,token:String)  =
        viewModelScope.launch(Dispatchers.IO) {
            _newDialogResponse.postValue(chatRepository.newChat(sendTo,token))
        }
}