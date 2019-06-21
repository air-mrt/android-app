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
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.api.UserApiService
import com.android.airmart.repository.CommentRepository
import com.android.airmart.repository.ProductRepository
import com.android.airmart.repository.UserRepository
import com.android.airmart.viewmodel.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getProductRepository(context: Context): ProductRepository {
        return ProductRepository(
                AppDatabase.getInstance(context.applicationContext).productDao(), ProductApiService.getInstance())
    }
    private fun getCommentRepository(context: Context): CommentRepository {
        return CommentRepository(
            AppDatabase.getInstance(context.applicationContext).commentDao())
    }
    private fun getUserRepository(context: Context): UserRepository {
        return UserRepository(
            AppDatabase.getInstance(context.applicationContext).userDao(), UserApiService.getInstance())
    }

    fun provideProductListViewModelFactory(context: Context): ProductListViewModelFactory {
        val repository = getProductRepository(context)
        return ProductListViewModelFactory(repository)
    }
    fun providePostHistoryViewModelFactory(context: Context): PostHistoryViewModelFactory {
        val repository = getProductRepository(context)
        val userepository = getUserRepository(context)
        return PostHistoryViewModelFactory(repository,userepository)
    }
    fun providePostDetailViewModelFactory(context: Context, productId:Long ): PostDetailViewModelFactory {
        val productRepository = getProductRepository(context)
        val commentRepository = getCommentRepository(context)
        return PostDetailViewModelFactory(productRepository,commentRepository, productId)
    }
    fun provideEditProductViewModelFactory(context: Context, productId:Long ): EditProductViewModelFactory {
        val productRepository = getProductRepository(context)
        return EditProductViewModelFactory(productRepository, productId)
    }
    fun providePostProductViewModelFactory(context: Context): PostProductViewModelFactory {
        val repository = getProductRepository(context)
        return PostProductViewModelFactory(repository)
    }
    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        val repository = getUserRepository(context)
        return LoginViewModelFactory(repository,context)
    }
    fun provideDashboardViewModelFactory(context: Context): DashboardViewModelFactory {
        val repository = getUserRepository(context)
        return DashboardViewModelFactory(repository)
    }
    fun provideGson(): Gson {
        return GsonBuilder().create()

    }

}
