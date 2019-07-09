package com.android.airmart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.dao.CommentDao
import com.android.airmart.data.dao.UserDao
import com.android.airmart.utilities.testComments
import com.android.airmart.utilities.testUser
import org.junit.After
import org.junit.Before
import org.junit.Rule

class UserDaoTest {
    private lateinit var database:AppDatabase
    private lateinit var userDao: UserDao
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase(){
        val context  = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context,AppDatabase::class.java).build()
        userDao = database.userDao()
        testUser.forEach{ User->
            database.userDao().insertUser(User)
        }
    }
    @After
    fun closeDatabase(){
        database.close()
    }

}