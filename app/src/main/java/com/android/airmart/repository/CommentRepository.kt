package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.dao.CommentDao
import com.android.airmart.data.entity.Comment
import javax.inject.Singleton

@Singleton
class CommentRepository constructor(private val commentDao: CommentDao) {

    fun allCommentsbyProductId(productId:Long): LiveData<List<Comment>> = commentDao.getAllCommentsByProductId(productId)
    fun insertComment(comment: Comment) = commentDao.insertComment(comment)
    fun updateComment(comment: Comment) = commentDao.updateComment(comment)
    fun deleteComment(comment: Comment) = commentDao.deleteComment(comment)
}