package com.android.airmart.ui.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.android.airmart.adapter.PostHistoryListAdapter
import com.android.airmart.databinding.FragmentPostHistoryBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.PostHistoryViewModel
import kotlinx.android.synthetic.main.fragment_post_history.*


class PostHistoryFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
    lateinit var username:String
    lateinit var token:String
    lateinit var totalPostsTextView:TextView
    val  postHistoryViewModel: PostHistoryViewModel by viewModels {
        InjectorUtils.providePostHistoryViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        totalPostsTextView =total_post_textView
        postHistoryViewModel.getUserInfo(token,username)
        updateTotalPosts(totalPostsTextView)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostHistoryBinding.inflate(inflater, container, false)
        val adapter = PostHistoryListAdapter()
        binding.recyclerView.adapter = adapter
        sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
        username = sharedPref.getString(USERNAME_KEY, DEFAULT_VALUE_SHARED_PREF)
        token = """Bearer ${sharedPref.getString(TOKEN_KEY,DEFAULT_VALUE_SHARED_PREF)}"""
        postHistoryViewModel.getAllProductsByUsername(username)
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: PostHistoryListAdapter) {
        postHistoryViewModel.allProductResponse.observe(viewLifecycleOwner, Observer { products ->
            if (products != null) adapter.submitList(products)
        })
    }
    private fun updateTotalPosts(totalPostsTextView:TextView){
        postHistoryViewModel.userInfoResponse?.observe(this, Observer {res->
            if (res!=null){
                totalPostsTextView.text = res.numberOfPosts
            }
        })
    }


}
