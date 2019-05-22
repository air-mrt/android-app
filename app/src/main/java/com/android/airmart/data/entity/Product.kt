package com.android.airmartversion1.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title:String,
    val description:String,
    val price:Int,
    val username:String

):Serializable