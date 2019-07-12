package com.android.airmart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.android.airmart.data.dao.UserDao
import com.android.airmart.utilities.testUser
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    @Test
    fun testInsertuser(){
        userDao.insertUser(testUser[1])
        assertThat(userDao.getAllUsers().value?.size,equalTo(2))
    }
    @Test
    fun testDeleteUser(){
        userDao.deleteUser(testUser[0])
        assertThat(userDao.getAllUsers().value?.size, equalTo(0))
    }



}