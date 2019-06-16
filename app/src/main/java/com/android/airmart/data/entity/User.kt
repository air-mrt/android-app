package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey val username: String,
    val name: String,
    val phone: String,
    val email: String,
    val profilePicture: String,
    val numberOfPosts: String


): Serializable