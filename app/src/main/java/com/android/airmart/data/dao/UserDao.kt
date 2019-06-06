package com.android.airmart.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.airmart.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * from user ORDER BY username")
    fun getAllUsers(): LiveData<List<User>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user : User):Long
    @Update
    fun updateUser(user: User):Int
    @Delete
    fun deleteUser(user: User):Int
    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getUsersByUsername(username: String): LiveData<User>
}