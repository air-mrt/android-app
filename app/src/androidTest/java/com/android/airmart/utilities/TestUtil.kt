package com.android.airmart.utilities

import android.app.Activity
import androidx.appcompat.widget.Toolbar
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
val testProducts = arrayListOf(
   Product(1, "product1", "product desc 1", "100","uri1","user1","",1),
    Product(2, "product1", "product desc 1", "100","uri12","user2","",2),
  Product(3, "product1", "product desc 1", "100","uri3","user3","",2)
)
val testproduct = testProducts[0]
val testUser = arrayListOf(
    User("","","","","",""),
    User("","","","","","")
)
fun getToolbarNavigationContentDescription(activity: Activity, toolbarId: Int) =
    activity.findViewById<Toolbar>(toolbarId).navigationContentDescription as String