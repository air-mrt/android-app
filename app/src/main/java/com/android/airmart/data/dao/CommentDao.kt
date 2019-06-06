package com.android.airmart.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.airmart.data.entity.Comment

@Dao
interface CommentDao {
    @Query("SELECT * from comment WHERE productId= :productId ORDER BY createdAt")
    fun getAllCommentsByProductId(productId:Int): LiveData<List<Comment>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment : Comment):Long
    @Update
    fun updateComment(comment : Comment):Int
    @Delete
    fun deleteComment(comment: Comment):Int
}