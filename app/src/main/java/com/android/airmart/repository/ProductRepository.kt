package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.api.ProductApiService
import com.android.airmart.data.api.model.ProductResponse
import com.android.airmart.data.dao.ProductDao
import com.android.airmart.data.entity.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class ProductRepository constructor(private val productDao: ProductDao, private val productApiService: ProductApiService) {

    fun allProductsRoom(): LiveData<List<Product>> = productDao.getAllProducts()
    fun insertProductRoom(product: Product) = productDao.insertProduct(product)
    fun updateProductRoom(product: Product) = productDao.updateProduct(product)
    fun deleteProductRoom(product: Product) = productDao.deleteProduct(product)
    fun getProductByTitleRoom(title: String): LiveData<Product> = productDao.getProductByTitle(title)
    fun getProductByIdRoom(productId: Long): LiveData<Product> = productDao.getProductById(productId)
    suspend fun getProductByIdApi(id: Long): Response<ProductResponse> =
        withContext(Dispatchers.IO){
            productApiService.getProductById(id).await()
        }
    suspend fun postProductApi(file: MultipartBody.Part?,productJson: RequestBody,token:String): Response<ProductResponse> =
            withContext(Dispatchers.IO){
                productApiService.postProduct(file, productJson, token).await()
            }
    suspend fun deleteProductByIdApi(id:Long, token :String):Response<Void> =
        withContext(Dispatchers.IO){
            productApiService.deleteProductById(id,token).await()
        }
}