package com.android.airmart.data.api.model

import java.util.*

data class ProductModel (
    val id:Long,
    val title:String,
    val price:String,
    val description:String,
    val createdAt: Date,
    val picturePath:String,
    val user: UserModel
    )