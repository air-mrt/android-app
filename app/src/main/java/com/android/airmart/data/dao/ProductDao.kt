package com.android.airmart.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.airmart.data.entity.Product

@Dao
interface ProductDao{
    @Query("SELECT * from product ORDER BY id")
    fun getAllProducts(): LiveData<List<Product>>
    @Query("SELECT * FROM product WHERE title = :title LIMIT 1")
    fun getProductByTitle(title:String):LiveData<Product>
    @Query("SELECT * FROM product WHERE username = :username ORDER BY createdAt")
    fun getAllProductsByUser(username:String):List<Product>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product : Product):Long
    @Update
    fun updateProduct(product: Product):Int
    @Delete
    fun deleteProduct(product: Product):Int
    @Query("SELECT * FROM product WHERE id = :productId LIMIT 1")
    fun getProductById(productId: Long): Product
    @Query("SELECT * FROM product WHERE id = :productId LIMIT 1")
    fun getProductByIdRoom(productId: Long): LiveData<Product>
    @Query("SELECT * FROM product WHERE  username= :username AND title like :query OR description like :query")
    fun searchProductsByUsername(username:String, query: String): List<Product>
}