package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["productId"], onDelete = ForeignKey.CASCADE)
    ]
    , indices = [Index(value = ["productId"])])
data class Comment(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val productId:Int,
    val content:String,
    val username:String,
    val createdAt:String = Date().toString()


): Serializable