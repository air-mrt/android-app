package com.android.airmart.data.entity


data class ChatMessage(
    val id: Long,
    val chat_id: Long,
    val message: String,
    val username: String,
    val avatar: String,
    val postedDate: String
)