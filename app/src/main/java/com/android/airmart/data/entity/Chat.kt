package com.android.airmart.data.entity

import com.android.airmart.ui.fragments.user.chat.models.Message
import com.stfalcon.chatkit.commons.models.IDialog


data class Chat(
val id:Long,
val message:String,
val owner:String,
val postedDate:String,
val users:Set<String>,
val messages:List<String>
)