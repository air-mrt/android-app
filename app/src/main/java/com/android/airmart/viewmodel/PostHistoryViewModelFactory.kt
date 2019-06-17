package com.android.airmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.airmart.repository.ProductRepository
import com.android.airmart.repository.UserRepository

class PostHistoryViewModelFactory(
    private val repository: ProductRepository,
    private val userRepository: UserRepository

) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = PostHistoryViewModel(repository,userRepository)  as T
}