package com.android.airmart.ui.fragments.user.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.android.airmart.R
import com.android.airmart.databinding.FragmentChatDialogBinding
import com.android.airmart.databinding.FragmentChatMessageBinding
import com.android.airmart.ui.fragments.user.chat.fixtures.MessagesFixtures
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.bumptech.glide.Glide
import com.muddzdev.styleabletoast.StyleableToast
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.fragment_chat_message.*

class ChatMessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = DataBindingUtil.inflate<FragmentChatMessageBinding>(
            inflater, R.layout.fragment_chat_message, container, false).apply{
            val messageListAdapter = MessagesListAdapter<Message>("username", ImageLoader { imageView, url ->
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView) })

            messagesList.setAdapter(messageListAdapter)
            input.setInputListener {input->
                messageListAdapter.addToStart(MessagesFixtures.getTextMessage(input.toString()),true)
                true
            }
        }
        return binding.root
    }


}
