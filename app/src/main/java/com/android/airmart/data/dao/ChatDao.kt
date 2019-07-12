package com.android.airmart.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatEntity

@Dao
interface ChatDao {
    @Insert
    fun send(chat: ChatEntity):Long
    @Query("SELECT * from ChatEntity LIMIT 1")
    fun getAll():ChatEntity

}