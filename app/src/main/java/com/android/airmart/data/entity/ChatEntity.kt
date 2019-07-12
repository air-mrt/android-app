package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.stfalcon.chatkit.commons.models.IDialog

@Entity
data class ChatEntity(
    @PrimaryKey val id:Long,
    val message:String,
    val owner:String,
    val postedDate:String,
    val users:String,
    val messages:String
)