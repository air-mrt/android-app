package com.android.airmart.ui.fragments.user.chat


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter

import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.android.airmart.R
import com.android.airmart.adapter.SearchResultListAdapter
import com.android.airmart.databinding.FragmentChatDialogBinding
import com.android.airmart.ui.fragments.user.chat.fixtures.DialogsFixtures
import com.android.airmart.ui.fragments.user.chat.models.Dialog
import com.android.airmart.ui.fragments.user.chat.models.Message
import com.android.airmart.ui.fragments.user.chat.models.User
import com.android.airmart.utilities.ChatUtil
import com.android.airmart.utilities.InjectorUtils
import com.android.airmart.utilities.SHARED_PREFERENCE_FILE
import com.android.airmart.utilities.SharedPrefUtil
import com.android.airmart.viewmodel.ChatViewModel
import com.android.airmart.viewmodel.ProductListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_chat_dialog.*


class ChatDialogFragment : Fragment() {
    private val chatViewModel: ChatViewModel by viewModels {
        InjectorUtils.provideChatViewModelFactory(requireContext())
    }
    lateinit var sharedPref: SharedPreferences
    lateinit var dialogsListAdapter:DialogsListAdapter<Dialog>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        chatViewModel.getallChats(SharedPrefUtil.getToken(sharedPref))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding  = DataBindingUtil.inflate<FragmentChatDialogBinding>(
            inflater, R.layout.fragment_chat_dialog, container, false).apply{
            dialogsListAdapter = DialogsListAdapter(ImageLoader { imageView, url ->
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView) })
            //dialogsListAdapter.setItems(DialogsFixtures.dialogs)
            dialogsList.setAdapter(dialogsListAdapter)
            dialogsListAdapter.setOnDialogClickListener {
                findNavController().navigate(ChatDialogFragmentDirections.actionChatDialogFragmentToChatMessageFragment(it.id.toLong()))
            }

            }
        subscribeUi(dialogsListAdapter)

        return binding.root
    }
    private fun subscribeUi(adapter: DialogsListAdapter<Dialog>) {
       chatViewModel.dialogResponse?.observe(viewLifecycleOwner, Observer {chats ->
            if (chats != null) adapter.setItems(ChatUtil.convertToDialog(chats))
        })

    }


}
