package com.android.airmart.ui.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R
import com.android.airmart.adapter.PostHistoryListAdapter
import com.android.airmart.databinding.FragmentPostHistoryBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.PostHistoryViewModel
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_post_history.*
import kotlinx.coroutines.Job


class PostHistoryFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
    lateinit var username:String
    lateinit var token:String
    lateinit var totalPostsTextView:TextView
    lateinit var adapter:PostHistoryListAdapter
    val  postHistoryViewModel: PostHistoryViewModel by viewModels {
        InjectorUtils.providePostHistoryViewModelFactory(requireContext())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        totalPostsTextView =total_post_textView
        updateTotalPosts(totalPostsTextView)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostHistoryBinding>(inflater,R.layout.fragment_post_history , container, false).apply {
            adapter = PostHistoryListAdapter(this@PostHistoryFragment)
            lifecycleOwner = this@PostHistoryFragment
            recyclerView.adapter = adapter
            sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
            username = sharedPref.getString(USERNAME_KEY, DEFAULT_VALUE_SHARED_PREF)
            token = """Bearer ${sharedPref.getString(TOKEN_KEY,DEFAULT_VALUE_SHARED_PREF)}"""
            postHistoryViewModel.getAllProductsByUsername(username)
            postHistoryViewModel.getUserInfo(token,username)
            executePendingBindings()
        }
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
     fun deletePost(productId:Long, token:String = this.token){
         val job = postHistoryViewModel.deletePostById(productId,token)
         val progress = showProgressBar()
         if(job.isActive){
             progress.show()
         }
        job.invokeOnCompletion {
            progress.dismiss()
                 if (job.isCancelled){
                    showErrorDialog().show()
                 }
         }


    }
    fun showProgressBar():MaterialDialog{
        return MaterialDialog
            .Builder(requireContext())
            .title("Deleting Post")
            .content("please wait..")
            .progress(true, 0)
            .build()
    }
    fun showErrorDialog():MaterialDialog{
        return MaterialDialog
            .Builder(requireContext())
            .title("Could not connect to server")
            .content("Unable to make connection to server. Make sure you have an internet connection and try again.")
            .positiveText("OK")
            .onPositive { dialog, which ->  dialog.dismiss()}
            .build()
    }

}
