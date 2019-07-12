package com.android.airmart.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatEntity
import com.android.airmart.data.entity.ChatMessageEntity

@Dao
interface MessageDao {
    @Insert
    fun add(chat: ChatEntity):Long
    @Query("SELECT * from ChatMessageEntity")
    fun getAll():ChatMessageEntity
}