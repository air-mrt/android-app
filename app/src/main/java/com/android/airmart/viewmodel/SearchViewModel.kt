package com.android.airmart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.airmart.data.entity.Product
import com.android.airmart.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel ( productRepository: ProductRepository, private val title: String
) : ViewModel() {

    fun search(titles:String) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }


}