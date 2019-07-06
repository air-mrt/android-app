package com.android.airmart.ui.fragments.user


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.android.airmart.R
import com.android.airmart.adapter.PostHistoryListAdapter
import com.android.airmart.databinding.FragmentPostHistoryBinding
import com.android.airmart.utilities.*
import com.android.airmart.viewmodel.PostHistoryViewModel
import kotlinx.android.synthetic.main.fragment_post_history.*


class PostHistoryFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPostHistoryBinding>(inflater,R.layout.fragment_post_history , container, false).apply {
            adapter = PostHistoryListAdapter(this@PostHistoryFragment)
            lifecycleOwner = this@PostHistoryFragment
            recyclerView.adapter = adapter
            searchListener = View.OnClickListener {

            }
            sharedPref = requireActivity().getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
            if (!SharedPrefUtil.isLoggedIn(sharedPref)){
                findNavController().navigate(R.id.loginFragment)
            }
            postHistoryViewModel.getAllProductsByUsername(SharedPrefUtil.getUsername(sharedPref))
            postHistoryViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref),SharedPrefUtil.getUsername(sharedPref))
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
        postHistoryViewModel.userInfoResponse?.observe(viewLifecycleOwner, Observer {res->
            if (res!=null){
                totalPostsTextView.text = res.numberOfPosts
            }
        })
    }
     fun deletePost(productId:Long, token:String = SharedPrefUtil.getToken(sharedPref)){
         val job = postHistoryViewModel.deletePostById(productId,token)
         val progress = showProgressBar()
         if(job.isActive){
             progress.show()
         }
        job.invokeOnCompletion {
            progress.dismiss()
            postHistoryViewModel.getAllProductsByUsername(SharedPrefUtil.getUsername(sharedPref))
            postHistoryViewModel.getUserInfo(SharedPrefUtil.getToken(sharedPref),SharedPrefUtil.getUsername(sharedPref))
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
