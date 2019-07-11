package com.android.airmart.ui.fragments.user.chat


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.android.airmart.R
import com.android.airmart.databinding.FragmentChatDialogBinding
import com.android.airmart.databinding.FragmentChatMessageBinding
import com.android.airmart.ui.fragments.user.EditProductFragmentArgs
import com.android.airmart.ui.fragments.user.chat.fixtures.MessagesFixtures
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.android.airmart.utilities.ChatUtil
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.utilities.SHARED_PREFERENCE_FILE
import com.android.airmart.utilities.SharedPrefUtil
import com.android.airmart.viewmodel.ChatViewModel
import com.bumptech.glide.Glide
import com.muddzdev.styleabletoast.StyleableToast
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.fragment_chat_message.*

class ChatMessageFragment : Fragment() {
    private val args: ChatMessageFragmentArgs by navArgs()
    private val chatViewModel: ChatViewModel by viewModels {
        InjectorUtils.provideChatViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var messageListAdapter:MessagesListAdapter<Message>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        chatViewModel.getallMessagesInChats(args.chatId,SharedPrefUtil.getToken(sharedPref))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = DataBindingUtil.inflate<FragmentChatMessageBinding>(
            inflater, R.layout.fragment_chat_message, container, false).apply{
            messageListAdapter = MessagesListAdapter<Message>("username", ImageLoader { imageView, url ->
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView) })

            messagesList.setAdapter(messageListAdapter)
            input.setInputListener {input->
                messageListAdapter.addToStart(MessagesFixtures.getTextMessage(input.toString()),true)
                true
            }
        }
        subscribeUi(messageListAdapter)
        return binding.root
    }
    private fun subscribeUi(adapter: MessagesListAdapter<Message>) {
        chatViewModel.messageResponse?.observe(viewLifecycleOwner, Observer {msg ->
            if (msg != null) adapter.addToEnd(ChatUtil.convertToMessage(msg),true)
        })

    }


}
