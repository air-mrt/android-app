package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey val username:String,
    val password:String,
    val name:String,
    val email:String,
    val phone:String


): Serializable