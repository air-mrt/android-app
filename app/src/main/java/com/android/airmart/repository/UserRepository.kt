package com.android.airmart.repository

import androidx.lifecycle.LiveData
import com.android.airmart.data.dao.UserDao
import com.android.airmart.data.entity.User
import javax.inject.Singleton

@Singleton
class UserRepository constructor(private val userDao: UserDao) {

    fun allUsers(): LiveData<List<User>> = userDao.getAllUsers()
    fun insertUser(product: User) = userDao.insertUser(product)
    fun updateUser(product: User) = userDao.updateUser(product)
    fun deleteUser(product: User) = userDao.deleteUser(product)
    fun getUserByUsername(username: String): LiveData<User> = userDao.getUsersByUsername(username)
}