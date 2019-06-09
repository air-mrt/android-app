package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
//foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["username"], childColumns = ["username"], onDelete = ForeignKey.CASCADE)], indices = [Index(value = ["username"])]
@Entity()
data class Product(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val title:String,
    val description:String,
    val price:String,
    val username:String,
    val createdAt: String = Date().toString()

):Serializable