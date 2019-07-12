package com.android.airmart.repository

import com.android.airmart.data.entity.Comment
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CommentRepoTest(){
    lateinit var comment: Comment
    @Before fun setUp() {
        comment = Comment(1, 1, "its good", "user1", "")
    }
    @Test
    fun commentnotnull(){
        assertTrue(comment.content.isEmpty())

    }
    @Test
    fun commenttostring(){
        assertEquals("user1",comment.username)
    }
    @Test
    fun delecomment(){
        val samplecoment = Comment(1,1,"fsghshghgs","user2","")
        val delete = samplecoment.toString().isEmpty()
        assertEquals(delete,samplecoment)

    }

}