package com.android.airmart.utilities

import com.android.airmart.data.entity.Comment
import com.android.airmart.data.entity.Product
import com.android.airmart.data.entity.User

/**
 * [Comment] objects used for tests.
 */
val testComments = arrayListOf(
    Comment(1, 1, "wow", "user1"),
    Comment(2,2, "very nice", "user2"),
    Comment(3, 3, "i don't like it", "user3")
)
//val testProducts = arrayListOf(
//    Product(1, "product1", "product desc 1", "100","uri1","user1"),
//    Product(2, "product1", "product desc 1", "100","uri12","user2"),
//    Product(3, "product1", "product desc 1", "100","uri3","user3")
//)
val testUser = arrayListOf(
    User("","","","","",""),
    User("","","","","","")
)