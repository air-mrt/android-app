package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.api.model.ProductModel
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class ProductRepository constructor(private val productDao: ProductDao, private val productApiService: ProductApiService) {

    fun allProducts(): LiveData<List<Product>> = productDao.getAllProducts()
    fun insertProduct(product: Product) = productDao.insertProduct(product)
    fun updateProduct(product: Product) = productDao.updateProduct(product)
    fun deleteProduct(product: Product) = productDao.deleteProduct(product)
    fun getProductByTitle(title: String): LiveData<Product> = productDao.getProductByTitle(title)
    fun getProductById(productId: Int): LiveData<Product> = productDao.getProductById(productId)
    suspend fun getProductByIdFromRetro(id: Long): Response<ProductModel> =
        withContext(Dispatchers.IO){
            productApiService.getProductById(id).await()
        }
    suspend fun postProduct(file: MultipartBody.Part,productJson: RequestBody,token:String): Response<ProductModel> =
            withContext(Dispatchers.IO){
                productApiService.postProduct(file, productJson, token).await()
            }
}