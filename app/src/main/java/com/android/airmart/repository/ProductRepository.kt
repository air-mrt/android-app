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
import java.net.ConnectException
import java.util.ArrayList
import javax.inject.Singleton

@Singleton
class ProductRepository constructor(private val productDao: ProductDao, private val productApiService: ProductApiService) {
    fun getProductById(id:Long) = productDao.getProductByIdRoom(id)
    fun allProductsRoom() = productDao.getAllProducts()
    suspend fun getProductByIdAPI(id: Long): Response<ProductResponse> =
        withContext(Dispatchers.IO){
            productApiService.getProductById(id).await()
        }
    suspend fun getAllProductsByUserAPI(username:String): Response<List<ProductResponse>> =
        withContext(Dispatchers.IO){
            productApiService.getAllProducts(username).await()
        }
    suspend fun getAllInterestedProductsAPI(username:String): Response<List<ProductResponse>> =
        withContext(Dispatchers.IO){
            productApiService.getAllInterestedProducts(username).await()
        }
    suspend fun searchResultAPI(keyword:String): Response<List<ProductResponse>> =
        withContext(Dispatchers.IO){
            productApiService.searchProduct(keyword).await()
        }
    suspend fun allProductsByUser(token:String, username:String):List<Product> =
        withContext(Dispatchers.IO){
            try{
                var usernameFromToken = ""
                var productsByUser= getAllProductsByUserAPI(token).body()
                if(productsByUser != null){
                    for (res in productsByUser){
                       usernameFromToken = res.username
                        var product = Product(res._id,res.username,res.title,res.price,res.description,res.pictureUrl,res.createdAt, res.interested.size)
                        productDao.insertProduct(product)
                    }
                    return@withContext productDao.getAllProductsByUser(usernameFromToken)
                }

            }
            catch (e:ConnectException){
                return@withContext productDao.getAllProductsByUser(username)
            }
            return@withContext productDao.getAllProductsByUser(username)
        }

    suspend fun allInterestedProductsByUser(username:String):List<Product>? =
        withContext(Dispatchers.IO){
            var productList = ArrayList<Product>()
            try{
                var productsInterestedByUser= getAllInterestedProductsAPI(username).body()
                if(productsInterestedByUser != null){
                    for (res in productsInterestedByUser){
                        // TODO add to a special room table
                        var product = Product(res._id,res.username,res.title,res.price,res.description,res.pictureUrl,res.createdAt, res.interested.size)
                        productList.add(product)
                    }
                    return@withContext return@withContext productList
                }

            }
            catch (e:ConnectException){
                return@withContext null
            }
            return@withContext null
        }


    suspend fun searchResult(keyword:String):List<Product>? =
        withContext(Dispatchers.IO){
            var productList = ArrayList<Product>()
            try{
                var products = searchResultAPI(keyword).body()
                if(products != null){
                    for (res in products){
                        var product = Product(res._id,res.username,res.title,res.price,res.description,res.pictureUrl,res.createdAt, res.interested.size)
                        productDao.insertProduct(product)
                        productList.add(product)
                    }
                    return@withContext productList
                }

            }
            catch (e:ConnectException){
                return@withContext null
            }
            return@withContext null
        }
    suspend fun deleteProductById(id:Long,token:String)=
        withContext(Dispatchers.IO){
            deleteProductByIdAPI(id,token)
            productDao.deleteProduct(productDao.getProductById(id))
            return@withContext true
        }

    suspend fun interested(id:Long,token:String)=
        withContext(Dispatchers.IO){
            interestedAPI(id,token)
            var product = productDao.getProductById(id)
            product.interested +=1
            productDao.updateProduct(product)
            return@withContext true
        }
    suspend fun uninterested(id:Long,token:String)=
        withContext(Dispatchers.IO){
            uninterestedAPI(id,token)
            var product = productDao.getProductById(id)
            productDao.updateProduct(product)
            return@withContext true
        }

    @Throws(ConnectException::class)
    suspend fun postProductAPI (file: MultipartBody.Part?,productJson: RequestBody,token:String): Response<ProductResponse> =
            withContext(Dispatchers.IO){
                productApiService.postProduct(file, productJson, token).await()
            }
    @Throws(ConnectException::class)
    suspend fun editProductAPI (id:Long,file: MultipartBody.Part?,productJson: RequestBody,token:String): Response<ProductResponse> =
        withContext(Dispatchers.IO){
            productApiService.editProduct(id,file, productJson, token).await()
        }
    suspend fun interestedAPI (id:Long,token:String): Response<ProductResponse> =
        withContext(Dispatchers.IO){
            productApiService.interested(id,token).await()
        }
    suspend fun uninterestedAPI (id:Long,token:String): Response<ProductResponse> =
        withContext(Dispatchers.IO){
            productApiService.uninterested(id,token).await()
        }
    suspend fun deleteProductByIdAPI(id:Long, token :String):Response<Void> =
        withContext(Dispatchers.IO){
            productApiService.deleteProductById(id,token).await()
        }
}