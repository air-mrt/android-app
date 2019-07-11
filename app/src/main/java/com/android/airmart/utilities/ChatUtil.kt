package com.android.airmart.utilities

import com.android.airmart.data.entity.Chat
import com.android.airmart.data.entity.ChatMessage
import com.android.airmart.data.entity.User
import com.android.airmart.ui.fragments.user.chat.models.Dialog
import com.android.airmart.ui.fragments.user.chat.models.Message
import java.util.*
import kotlin.collections.ArrayList

object ChatUtil {
    fun convertToDialog(chat: Chat):Dialog{
        val usernames = chat.message.split(",")
        var users = ArrayList<com.android.airmart.ui.fragments.user.chat.models.User>()
        for (user in usernames){
            val u = com.android.airmart.ui.fragments.user.chat.models.User(user,user,user,true)
            users.add(u)
        }
        return Dialog(chat.id.toString(),chat.message,"default_photo",users,null,0)
    }
    fun convertToDialog(chats:List<Chat>):List<Dialog>{
        var dialogs = ArrayList<Dialog>()
        for(chat:Chat in chats){
            dialogs.add(convertToDialog(chat))
        }
        return dialogs
    }
    fun convertToMessage(chatMessage: ChatMessage):Message{
        val user = com.android.airmart.ui.fragments.user.chat.models.User(chatMessage.username,chatMessage.username,chatMessage.avatar,true)
        //TODO parse string date
        return Message(chatMessage.id.toString(),user,chatMessage.message,Date())

    }
    fun convertToMessage( msgs:List<ChatMessage>):List<Message>{
        var messages =ArrayList<Message>()
        for (message:ChatMessage in msgs){
            messages.add(convertToMessage(message))
        }
        return messages
    }


}