package com.android.airmart.ui.fragments.user.chat.fixtures

import com.android.airmart.ui.fragments.user.chat.models.Dialog
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.android.airmart.ui.fragments.user.chat.models.User
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/*
 * Created by Anton Bevza on 07.09.16.
 */
class DialogsFixtures private constructor() : FixturesData() {
    init {
        throw AssertionError()
    }

    companion object {

        val dialogs: ArrayList<Dialog>
            get() {
                val chats = ArrayList<Dialog>()

                for (i in 0..19) {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, -(i * i))
                    calendar.add(Calendar.MINUTE, -(i * i))

                    chats.add(getDialog(i, calendar.time))
                }

                return chats
            }

        private fun getDialog(i: Int, lastMessageCreatedAt: Date): Dialog {
            val users = users
            return Dialog(
                FixturesData.randomId,
                if (users.size > 1) FixturesData.groupChatTitles[users.size - 2] else users[0].name,
                if (users.size > 1) FixturesData.groupChatImages[users.size - 2] else FixturesData.randomAvatar,
                users,
                getMessage(lastMessageCreatedAt),
                if (i < 3) 3 - i else 0
            )
        }

        private val users: ArrayList<User>
            get() {
                val users = ArrayList<User>()
                val usersCount = 1 + FixturesData.rnd.nextInt(4)

                for (i in 0 until usersCount) {
                    users.add(user)
                }

                return users
            }

        private val user: User
            get() = User(
                FixturesData.randomId,
                FixturesData.randomName,
                FixturesData.randomAvatar,
                FixturesData.randomBoolean
            )

        private fun getMessage(date: Date): Message {
            return Message(
                FixturesData.randomId,
                user,
                FixturesData.randomMessage,
                date
            )
        }
    }
}
