package com.android.airmart.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessageEntity(
    @PrimaryKey val id: Long,
    val chat_id: Long,
    val message: String,
    val username: String,
    val avatar: String,
    val postedDate: String
)