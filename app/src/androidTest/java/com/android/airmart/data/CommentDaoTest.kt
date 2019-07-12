package com.android.airmart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.dao.CommentDao
import com.android.airmart.data.entity.Comment
import com.android.airmart.utilities.testComments
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CommentDaoTest{
    private lateinit var database:AppDatabase
    private lateinit var commentDao: CommentDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDatabase(){
        val context  = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        commentDao = database.commentDao()
        testComments.forEach{ comment->
            database.commentDao().insertComment(comment)
        }
    }
    @After fun closeDatabase(){
        database.close()
    }

    @Test fun testGetAllCommentsByProductId(){
        assertThat(commentDao.getAllCommentsByProductId(1).value?.size,equalTo(1))
    }
    @Test fun testGetAllCommentsByProductId_nonexistentId(){
        assertThat(commentDao.getAllCommentsByProductId(1111).value?.size,equalTo(0))
    }
    @Test fun testInsertComment(){
        commentDao.insertComment(Comment(1,1,"description","username"))
        assertThat(commentDao.getAllCommentsByProductId(1).value?.size, equalTo(2))
    }
    @Test fun testDeleteComment(){
        commentDao.deleteComment(testComments[0])
        assertThat(commentDao.getAllCommentsByProductId(1).value?.size, equalTo(0))
    }

}