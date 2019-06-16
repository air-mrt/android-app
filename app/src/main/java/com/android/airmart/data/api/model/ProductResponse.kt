package com.android.airmart.data.api.model

import java.util.*

data class ProductResponse (
    val _id:Long,
    val username: String,
    val title:String,
    val price:String,
    val description:String,
    val pictureUrl:String,
    val createdAt: String,
    val interested: Set<String>


    )