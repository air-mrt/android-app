package com.android.airmart.ui.fragments.user.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter

import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.airmart.R
import com.android.airmart.databinding.FragmentChatDialogBinding
import com.android.airmart.ui.fragments.user.chat.fixtures.DialogsFixtures
import com.android.airmart.ui.fragments.user.chat.models.Dialog
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.android.airmart.ui.fragments.user.chat.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_chat_dialog.*


class ChatDialogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = DataBindingUtil.inflate<FragmentChatDialogBinding>(
            inflater, R.layout.fragment_chat_dialog, container, false).apply{
            val dialogsListAdapter = DialogsListAdapter<Dialog>(ImageLoader { imageView, url ->
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView) })
            dialogsListAdapter.setItems(DialogsFixtures.dialogs)
            dialogsList.setAdapter(dialogsListAdapter)
            dialogsListAdapter.setOnDialogClickListener {
                findNavController().navigate(ChatDialogFragmentDirections.actionChatDialogFragmentToChatMessageFragment())

            }

            }

        return binding.root
    }


}
