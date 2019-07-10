package com.android.airmart.ui.fragments.user.chat.models

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType

import java.util.Date

/*
 * Created by troy379 on 04.04.17.
 */
class Message @JvmOverloads constructor(
    private val id: String,
    private val user: User,
    private var text: String?,
    private var createdAt: Date? = Date()
) : IMessage {
    val status: String
        get() = "Sent"

    override fun getId(): String {
        return id
    }

    override fun getText(): String? {
        return text
    }

    override fun getCreatedAt(): Date? {
        return createdAt
    }

    override fun getUser(): User {
        return this.user
    }



    fun setText(text: String) {
        this.text = text
    }

    fun setCreatedAt(createdAt: Date) {
        this.createdAt = createdAt
    }
}
