package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id:Long,
    val username: String,
    val title:String,
    val price:String,
    val description:String,
    val pictureUrl:String,
    val createdAt: String,
    var interested: Int

):Serializable