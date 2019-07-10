package com.android.airmart.ui.fragments.user.chat.fixtures

import com.android.airmart.ui.fragments.user.chat.models.Message
import com.android.airmart.ui.fragments.user.chat.models.User
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/*
 * Created by troy379 on 12.12.16.
 */
class MessagesFixtures private constructor() : FixturesData() {
    init {
        throw AssertionError()
    }

    companion object {

        val textMessage: Message
            get() = getTextMessage(FixturesData.Companion.randomMessage)

        fun getTextMessage(text: String): Message {
            return Message(FixturesData.Companion.randomId, user, text)
        }

        fun getMessages(startDate: Date?): ArrayList<Message> {
            val messages = ArrayList<Message>()
            for (i in 0..9/*days count*/) {
                val countPerDay = FixturesData.Companion.rnd.nextInt(5) + 1

                for (j in 0 until countPerDay) {
                    val message: Message
                    message = textMessage
                    val calendar = Calendar.getInstance()
                    if (startDate != null) calendar.time = startDate
                    calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1))

                    message.setCreatedAt(calendar.time)
                    messages.add(message)
                }
            }
            return messages
        }

        private val user: User
            get() {
                val even = FixturesData.Companion.rnd.nextBoolean()
                return User(
                    if (even) "0" else "1",
                    if (even) FixturesData.Companion.names[0] else FixturesData.Companion.names[1],
                    if (even) FixturesData.Companion.avatars[0] else FixturesData.Companion.avatars[1],
                    true
                )
            }
    }
}
