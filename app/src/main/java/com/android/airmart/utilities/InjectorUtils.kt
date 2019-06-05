/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.airmart.utilities

import android.content.Context
import com.android.airmart.data.AppDatabase
import com.android.airmart.repository.ProductRepository
import com.android.airmart.viewmodel.PostDetailViewModelFactory
import com.android.airmart.viewmodel.ProductListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getProductRepository(context: Context): ProductRepository {
        return ProductRepository(
                AppDatabase.getInstance(context.applicationContext).productDao())
    }

    fun provideProductListViewModelFactory(context: Context): ProductListViewModelFactory {
        val repository = getProductRepository(context)
        return ProductListViewModelFactory(repository)
    }
    fun providePostDetailViewModelFactory(context: Context, productId:Int ): PostDetailViewModelFactory {
        val repository = getProductRepository(context)
        return PostDetailViewModelFactory(repository, productId)
    }

}
