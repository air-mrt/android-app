package com.android.airmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.airmart.repository.ChatRepository
import com.android.airmart.repository.UserRepository

class ChatViewModelFactory(
    private val repository: ChatRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = ChatViewModel(repository) as T


}