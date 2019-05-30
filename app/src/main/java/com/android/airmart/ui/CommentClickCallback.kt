package com.android.airmart.ui

import com.android.airmart.data.entity.Product


interface CommentClickCallback {
    fun onClick(product: Product)
}
