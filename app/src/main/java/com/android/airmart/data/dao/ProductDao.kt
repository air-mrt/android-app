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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product : Product):Long
    @Update
    fun updateProduct(product: Product):Int
    @Delete
    fun deleteProduct(product: Product):Int
    @Query("SELECT * FROM product WHERE id = :productId")
    fun getProductById(productId: Int): LiveData<Product>
}